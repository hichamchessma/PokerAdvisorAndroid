/*PlayerInfoDialog*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pokeradvisorapp.R

@Composable
fun PlayerInfoDialog(
    playerInfo: PlayerInfo,
    playerIndex: Int,
    onDismiss: (PlayerInfo?) -> Unit,
    focusOnStack: Boolean = false,
    allPlayerInfo: Map<Int, PlayerInfo>,
    positionPlayer: String,
    smallBlindPosition: Int?, // Ajoutez cette ligne
    bigBlindPosition: Int?, // Ajoutez cette ligne
    bigBlindAmount: Int,
    updatePot: (Int) -> Unit
) {
    var stack by remember { mutableStateOf(playerInfo.stack) }
    var lastAction by remember { mutableStateOf(playerInfo.lastAction) } // Fold par défaut
    var actualMood by remember { mutableStateOf(playerInfo.actualMood) }
    var historyActions by remember { mutableStateOf(playerInfo.historyActions.joinToString(separator = ", ")) }
    var betAmount by remember { mutableStateOf(playerInfo.betAmount) }
    var lastBetAmount by remember { mutableStateOf(playerInfo.lastBetAmount) }
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
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number // Utilise un clavier numérique
                            ),
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

                        val isStackTooSmall = (stack.toIntOrNull() ?: 0) <= highestBetAmount

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
                                        onDismiss(playerInfo.copy(
                                            inOut = false,
                                            lastAction = lastAction,
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )) // Fermer le dialog immédiatement
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
                                        onDismiss(playerInfo.copy(
                                            lastAction = lastAction,
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )) // Fermer le dialog immédiatement
                                    },
                                    enabled = !isStackTooSmall,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = if (isStackTooSmall) 0.4f else 1f)
                                    )
                                ) {
                                    Text(text = "Check", color = MaterialTheme.colorScheme.onPrimary)
                                }

                                // Bouton Call
                                Button(
                                    onClick = {
                                        lastAction = "Call"
                                        if (highestBetAmount == bigBlindAmount && playerIndex==smallBlindPosition) {

                                                    val callAmount = bigBlindAmount / 2
                                                    betAmount = (lastBetAmount.toInt() + callAmount).toString()
                                                    stack = (stack.toIntOrNull()?.minus(callAmount) ?: 0).toString()
                                                    updatePot(callAmount)
                                                    historyActions += ", C$betAmount"
                                                    lastBetAmount=betAmount

                                        }
                                        else {
                                            val callAmount = highestBetAmount - lastBetAmount.toInt()
                                            betAmount = highestBetAmount.toString()
                                            stack = (stack.toIntOrNull()?.minus(callAmount) ?: 0).toString()
                                            updatePot(callAmount) // Ajouter la mise au pot via le callback
                                            historyActions += ", C$betAmount"
                                            lastBetAmount = betAmount
                                        }
                                        onDismiss(
                                            playerInfo.copy(
                                                stack = stack,
                                                lastAction = lastAction,
                                                betAmount = betAmount,
                                                lastBetAmount = lastBetAmount,
                                                historyActions = historyActions.split(", ").map { it.trim() }
                                            )
                                        ) // Fermer le dialog immédiatement
                                    },
                                    enabled = !isStackTooSmall && !(playerIndex == bigBlindPosition && highestBetAmount == bigBlindAmount),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = if (isStackTooSmall) 0.4f else 1f)
                                    )
                                ){
                                    Text(
                                        text = "Call ${if (playerIndex == smallBlindPosition && highestBetAmount == bigBlindAmount) bigBlindAmount / 2 else (highestBetAmount-lastBetAmount.toInt())}",
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
                                        isRaiseSelected =true
                                    },
                                    enabled = !isStackTooSmall,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = if (isStackTooSmall) 0.4f else 1f)
                                    )                                ) {
                                    Text(
                                        text = "Raise",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                // Bouton All In
                                Button(
                                    onClick = {

                                        lastAction = "All In"
                                        val allInAmount = stack.toIntOrNull() ?: 0
                                        if (allInAmount > 0) {
                                            betAmount = (allInAmount+lastBetAmount.toInt()).toString()
                                            stack = "0"
                                            updatePot(allInAmount)
                                            historyActions += ", Ai$betAmount"
                                        }


                                        val updatedPlayerInfo = PlayerInfo(
                                            inOut = inOut,
                                            stack = stack,
                                            lastAction = lastAction,
                                            actualMood = actualMood,
                                            betAmount = betAmount,
                                            lastBetAmount = lastBetAmount,
                                            historyActions = historyActions.split(", ").map { it.trim() }
                                        )
                                        onDismiss(updatedPlayerInfo)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(
                                        text = "All In",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }

                            var isIllegalRaise by remember { mutableStateOf(false) }

                            if (isRaiseSelected) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    TextField(
                                        value = betAmount,
                                        onValueChange = {
                                            betAmount = it
                                            val raiseAmount = it.toIntOrNull() ?: 0
                                            val currentStack = stack.toIntOrNull() ?: 0
                                            isIllegalRaise = (raiseAmount < 2 * highestBetAmount && raiseAmount != currentStack) || raiseAmount >= currentStack
                                        },
                                        label = { Text("Montant du Raise") },
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        modifier = Modifier.focusRequester(focusRequester)
                                    )

                                    LaunchedEffect(Unit) {
                                        focusRequester.requestFocus() // Focaliser automatiquement sur le champ de saisie
                                    }

                                    // Message d'erreur pour Raise invalide
                                    if (isIllegalRaise) {
                                        Text(
                                            text = "raise invalide !",
                                            color = androidx.compose.ui.graphics.Color.Red,
                                            modifier = Modifier.padding(top = 4.dp),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Bouton de confirmation pour Raise
                                    Button(
                                        onClick = {
                                            val raiseAmount = betAmount.toIntOrNull() ?: 0
                                            if (!isIllegalRaise) {
                                                // Mettre à jour les informations du joueur
                                                betAmount=(raiseAmount-lastBetAmount.toInt()).toString()
                                                stack = (stack.toIntOrNull()?.minus(betAmount.toInt()) ?: 0).toString()
                                                updatePot(raiseAmount)
                                                historyActions += ", R$betAmount"
                                                lastBetAmount=betAmount

                                                // Créer l'objet PlayerInfo mis à jour et fermer le dialog
                                                val updatedPlayerInfo = PlayerInfo(
                                                    inOut = inOut,
                                                    stack = stack,
                                                    lastAction = "Raise",
                                                    actualMood = actualMood,
                                                    betAmount = raiseAmount.toString(),
                                                    lastBetAmount = lastBetAmount,
                                                    historyActions = historyActions.split(", ").map { it.trim() }
                                                )
                                                onDismiss(updatedPlayerInfo) // Fermer le dialog immédiatement
                                            }
                                        },
                                        enabled = !isIllegalRaise, // Désactiver si le raise est invalide
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = "Confirmer Raise", color = MaterialTheme.colorScheme.onPrimary)
                                    }
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
    var inOut: Boolean = false,
    var lastBetAmount: String = ""
) {
    var inOutState by mutableStateOf(inOut)
}
