/*PlayerInfoDialog*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp

@Composable
fun PlayerInfoDialog(playerInfo: PlayerInfo, playerIndex: Int, onDismiss: (PlayerInfo?) -> Unit) {
    var stack by remember { mutableStateOf(playerInfo.stack) }
    var lastAction by remember { mutableStateOf(playerInfo.lastAction) } // Fold par défaut
    var actualMood by remember { mutableStateOf(playerInfo.actualMood) }
    var historyActions by remember { mutableStateOf(playerInfo.historyActions.joinToString(separator = ", ")) }
    var expanded by remember { mutableStateOf(false) }
    var raiseAmount by remember { mutableStateOf(playerInfo.raiseAmount) }
    var isRaiseSelected by remember { mutableStateOf(false) }
    var isCallSelected by remember { mutableStateOf(false) }
    var inOut by remember { mutableStateOf(playerInfo.inOut) }

    val actions = listOf("Check", "Call", "Raise", "All in", "Fold")
    isRaiseSelected = playerInfo.lastAction == "Raise"
    isCallSelected = playerInfo.lastAction == "Call"
    val focusRequester = remember { FocusRequester() }
    var callAmount by remember { mutableStateOf(playerInfo.callAmount) }
    AlertDialog(
        onDismissRequest = { onDismiss(null) },
        confirmButton = {
            Button(onClick = {
                val updatedPlayerInfo = PlayerInfo(
                    inOut = inOut,
                    stack = stack,
                    lastAction = lastAction,
                    actualMood = actualMood,
                    raiseAmount = if (isRaiseSelected) raiseAmount else "",
                    callAmount = if (isCallSelected) callAmount else "",
                    historyActions = historyActions.split(", ").map { it.trim() }
                )
                onDismiss(updatedPlayerInfo)
            }) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss(null) }) {
                Text("Annuler")
            }
        },
        title = { Text(text = "Player ${playerIndex + 1}") }, // Modifier le titre
        text = {
            Column {
                Text(text = "Joueur In/Out")
                Switch(
                    checked = inOut,
                    onCheckedChange = { inOut = it }
                )

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = stack,
                    onValueChange = { stack = it },
                    label = { Text("Stack") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Menu déroulant pour "Dernière action"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dernière action :",
                        modifier = Modifier
                            .weight(1f)
                            .clickable { expanded = true }
                            .padding(end = 8.dp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        actions.forEach { action ->
                            DropdownMenuItem(
                                text = { Text(text = action) },
                                onClick = {
                                    lastAction = action
                                    expanded = false
                                    isRaiseSelected = action == "Raise"
                                    isCallSelected = action == "Call"
                                    if (!isRaiseSelected) {
                                        raiseAmount = ""
                                    }
                                    if (!isCallSelected) {
                                        callAmount = ""
                                    }
                                }
                            )
                        }
                    }
                }

                if (isRaiseSelected) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Raise",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        TextField(
                            value = raiseAmount,
                            onValueChange = { raiseAmount = it },
                            modifier = Modifier
                                .weight(2f)
                                .focusRequester(focusRequester)
                        )
                    }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }

                if (isCallSelected) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Call",
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        TextField(
                            value = callAmount,
                            onValueChange = { callAmount = it },
                            modifier = Modifier
                                .weight(2f)
                                .focusRequester(focusRequester)
                        )
                    }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = actualMood,
                    onValueChange = { actualMood = it },
                    label = { Text("Humeur actuelle") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = historyActions,
                    onValueChange = { historyActions = it },
                    label = { Text("Historique des actions") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

data class PlayerInfo(
    var stack: String = "",
    var lastAction: String = "",
    var actualMood: String = "",
    var raiseAmount: String = "",
    var callAmount: String = "",
    var historyActions: List<String> = emptyList(),
    var inOut: Boolean = false
){
    var inOutState by mutableStateOf(inOut)
}

data class TableInfo(
    val playersNumber: Int,
    val blinds: String,
    val hand: String,
    val buttonPosition: Int,
    val myPosition: Int,
    val myStack: String
)

data class PlayerInfoDetails(
    val stack: String,
    val action: String,
    val mood: String,
    val historyActions: List<String>
)
