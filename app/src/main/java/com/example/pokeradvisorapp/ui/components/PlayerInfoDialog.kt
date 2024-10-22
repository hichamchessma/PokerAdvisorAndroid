/*PlayerInfoDialog*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokeradvisorapp.R

@Composable
fun PlayerInfoDialog(
    playerInfo: PlayerInfo,
    playerIndex: Int,
    onDismiss: (PlayerInfo?) -> Unit,
    focusOnStack: Boolean = false,
    allPlayerInfo: Map<Int, PlayerInfo>,
    positionPlayer: String
) {
    var stack by remember { mutableStateOf(playerInfo.stack) }
    var lastAction by remember { mutableStateOf(playerInfo.lastAction) } // Fold par défaut
    var actualMood by remember { mutableStateOf(playerInfo.actualMood) }
    var historyActions by remember { mutableStateOf(playerInfo.historyActions.joinToString(separator = ", ")) }
    var expanded by remember { mutableStateOf(false) }
    var betAmount by remember { mutableStateOf(playerInfo.betAmount) }
    var isRaiseSelected by remember { mutableStateOf(false) }
    var isCallSelected by remember { mutableStateOf(false) }
    var inOut by remember { mutableStateOf(playerInfo.inOut) }
    val highestBetAmount = allPlayerInfo.values
        .filter { it.inOut } // Considérer seulement les joueurs qui sont in
        .maxOfOrNull { it.betAmount.toIntOrNull() ?: 0 } ?: 0
    val actions = listOf("Check", "Call ($highestBetAmount)", "Raise", "All in", "Fold")

    isRaiseSelected = playerInfo.lastAction == "Raise"
    isCallSelected = playerInfo.lastAction == "Call"
    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        onDismissRequest = { onDismiss(null) },
        confirmButton = {
            Button(onClick = {
                val updatedPlayerInfo = PlayerInfo(
                    inOut = inOut,
                    stack = stack,
                    lastAction = lastAction,
                    actualMood = actualMood,
                    betAmount = if (isRaiseSelected || isCallSelected) betAmount else "",
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
        text = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Ajouter l'image d'arrière-plan
                Image(
                    painter = painterResource(id = R.drawable.background), // Remplacez par le nom de votre ressource drawable
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.1f // Transparence légère pour que le contenu principal soit bien visible
                )

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (playerIndex == positionPlayer.toIntOrNull()) "Moi" else "Player $playerIndex",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        TextField(
                            value = stack,
                            onValueChange = { stack = it },
                            label = { Text("Stack") },
                            modifier = Modifier
                                .width(100.dp)
                                .focusRequester(focusRequester)
                        )
                        LaunchedEffect(Unit) {
                            if (focusOnStack) {
                                focusRequester.requestFocus() // Applique le focus si demandé
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))






                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            // Première ligne : Fold, Check, Call
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Bouton Fold
                                Button(
                                    onClick = {
                                        lastAction = "Fold"
                                        inOut = false // Le joueur n'est plus "in"
                                        historyActions += ", F" // Ajouter "F" à l'historique

                                        // Créer l'objet PlayerInfo mis à jour et fermer le dialog
                                        val updatedPlayerInfo = PlayerInfo(
                                            inOut = inOut,
                                            stack = stack,
                                            lastAction = lastAction,
                                            actualMood = actualMood,
                                            betAmount = "",
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )
                                        onDismiss(updatedPlayerInfo) // Fermer le dialog immédiatement
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Fold", color = MaterialTheme.colorScheme.onPrimary)
                                }


                                // Bouton Check
                                Button(
                                    onClick = {
                                        lastAction = "Check"
                                        historyActions += ", Ch" // Ajouter "Ch" à l'historique

                                        // Créer l'objet PlayerInfo mis à jour et fermer le dialog
                                        val updatedPlayerInfo = PlayerInfo(
                                            inOut = inOut,
                                            stack = stack,
                                            lastAction = lastAction,
                                            actualMood = actualMood,
                                            betAmount = "",
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )
                                        onDismiss(updatedPlayerInfo) // Fermer le dialog immédiatement
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Check", color = MaterialTheme.colorScheme.onPrimary)
                                }

                                // Bouton Call
                                Button(
                                    onClick = {
                                        lastAction = "Call"
                                        betAmount = highestBetAmount.toString() // Assigner le montant du Call
                                        stack = (stack.toIntOrNull()?.minus(highestBetAmount) ?: 0).toString() // Déduit la mise du stack
                                        historyActions += ", C$highestBetAmount" // Ajouter "C<montant>" à l'historique

                                        // Créer l'objet PlayerInfo mis à jour et fermer le dialog
                                        val updatedPlayerInfo = PlayerInfo(
                                            inOut = inOut,
                                            stack = stack,
                                            lastAction = lastAction,
                                            actualMood = actualMood,
                                            betAmount = betAmount,
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )
                                        onDismiss(updatedPlayerInfo) // Fermer le dialog immédiatement
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(
                                        text = "Call $highestBetAmount",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }

                            // Deuxième ligne : Raise, All In
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Bouton Raise
                                Button(
                                    onClick = {
                                        lastAction = "Raise"
                                        isRaiseSelected =
                                            true // Pour ouvrir le champ de saisie de la mise (TextField)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(
                                        text = "Raise",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                // Bouton All In
                                Button(
                                    onClick = {
                                        // Logique de All In à implémenter plus tard
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(
                                        text = "All In",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
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
        }
    )
}

data class PlayerInfo(
    var stack: String = "",
    var lastAction: String = "",
    var actualMood: String = "",
    var betAmount: String = "",
    var historyActions: List<String> = emptyList(),
    var inOut: Boolean = false
) {
    var inOutState by mutableStateOf(inOut)
}
