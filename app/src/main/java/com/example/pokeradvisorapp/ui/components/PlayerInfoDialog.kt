/*PokerAdvisorScreen*/
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PlayerInfoDialog(playerInfo: PlayerInfo, onDismiss: (PlayerInfo?) -> Unit) {
    var stack by remember { mutableStateOf(playerInfo.stack) }
    var lastAction by remember { mutableStateOf("Fold") } // Fold par défaut
    var actualMood by remember { mutableStateOf(playerInfo.actualMood) }
    var bouton by remember { mutableStateOf(playerInfo.bouton) }

    val actions = listOf("Check", "Call", "Raise", "All in")
    var expanded by remember { mutableStateOf(false) }
    var raiseAmount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss(null) },
        confirmButton = {
            Button(onClick = { onDismiss(PlayerInfo(stack, lastAction, actualMood, bouton)) }) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss(null) }) {
                Text("Annuler")
            }
        },
        title = { Text(text = "Informations sur le joueur") },
        text = {
            Column {
                TextField(
                    value = stack,
                    onValueChange = { stack = it },
                    label = { Text("Stack") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Menu déroulant pour "Dernière action"
                Text(
                    text = "Dernière action : $lastAction",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(vertical = 8.dp)
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
                            }
                        )
                    }
                }

                // Champ pour entrer le montant du bet si "Raise" est sélectionné
                if (lastAction == "Raise") {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = raiseAmount,
                        onValueChange = { raiseAmount = it },
                        label = { Text("Montant du pari") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = actualMood,
                    onValueChange = { actualMood = it },
                    label = { Text("Humeur actuelle") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Bouton")
                    Switch(checked = bouton, onCheckedChange = { bouton = it })
                }
            }
        }
    )
}

data class PlayerInfo(
    var stack: String = "",
    var lastAction: String = "",
    var actualMood: String = "",
    var bouton: Boolean = false
)

data class TableInfo(
    val playersNumber: Int,
    val blinds: String,
    val hand: String,
    val step: String,
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
