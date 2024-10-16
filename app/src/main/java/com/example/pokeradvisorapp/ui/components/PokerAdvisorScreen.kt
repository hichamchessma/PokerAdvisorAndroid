/*PokerAdvisorScreen*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PokerAdvisorScreen() {
    val context = LocalContext.current

    var nbrPlayers by remember { mutableStateOf("") }
    var expandedNbrPlayers by remember { mutableStateOf(false) }
    val playersOptions = (2..9).toList()

    var bigBlind by remember { mutableStateOf("") }
    var expandedBigBlind by remember { mutableStateOf(false) }
    val bigBlindOptions = listOf("20", "40", "100", "200")

    var positionPlayer by remember { mutableStateOf("") }
    var positionButton by remember { mutableStateOf("") }
    var expandedPosition by remember { mutableStateOf(false) }
    var expandedPositionButton by remember { mutableStateOf(false) }
    val positionOptions = (1..9).toList()
    val positionButtonOptions = (1..9).toList()

    // Card Selection States
    var card1 by remember { mutableStateOf("") }
    var card2 by remember { mutableStateOf("") }

    var flop1 by remember { mutableStateOf("") }
    var flop2 by remember { mutableStateOf("") }
    var flop3 by remember { mutableStateOf("") }
    var turn by remember { mutableStateOf("") }
    var river by remember { mutableStateOf("") }

    var expandedCard1 by remember { mutableStateOf(false) }
    var expandedCard2 by remember { mutableStateOf(false) }
    var expandedFlop1 by remember { mutableStateOf(false) }
    var expandedFlop2 by remember { mutableStateOf(false) }
    var expandedFlop3 by remember { mutableStateOf(false) }
    var expandedTurn by remember { mutableStateOf(false) }
    var expandedRiver by remember { mutableStateOf(false) }

    val cards = listOf("A♠", "A♥", "A♦", "A♣", "K♠", "K♥", "K♦", "K♣", "Q♠", "Q♥", "Q♦", "Q♣", "J♠", "J♥", "J♦", "J♣", "10♠", "10♥", "10♦", "10♣", "9♠", "9♥", "9♦", "9♣", "8♠", "8♥", "8♦", "8♣", "7♠", "7♥", "7♦", "7♣", "6♠", "6♥", "6♦", "6♣", "5♠", "5♥", "5♦", "5♣", "4♠", "4♥", "4♦", "4♣", "3♠", "3♥", "3♦", "3♣", "2♠", "2♥", "2♦", "2♣")

    val modes = listOf("Agressif", "Passif", "Adaptatif")
    var selectedMode by remember { mutableStateOf("") }
    var expandedMode by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var adviceText by remember { mutableStateOf("") }

    // State for tracking player-specific information
    val playerInfo = remember { mutableStateMapOf<Int, PlayerInfo>() }
    var currentPlayerIndex by remember { mutableStateOf<Int?>(null) }

    // State for tracking my actions
    val myHistory = remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Table de poker avec les joueurs
        PokerTableComponent(
            playerInfo = playerInfo,
            currentPlayerIndex = currentPlayerIndex,
            onPlayerClick = { index -> currentPlayerIndex = index },
            myPositionIndex = positionPlayer.toIntOrNull(),
            buttonPosition = positionButton.toIntOrNull()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Menu déroulant pour "Nombre de joueurs"
        Text(
            text = if (nbrPlayers.isEmpty()) "Nombre de joueurs à la table" else "Nombre de joueurs : $nbrPlayers",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedNbrPlayers = true }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expandedNbrPlayers,
            onDismissRequest = { expandedNbrPlayers = false }
        ) {
            playersOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        nbrPlayers = option.toString()
                        expandedNbrPlayers = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu déroulant pour "Big Blind"
        BigBlindSelector(bigBlind, expandedBigBlind, bigBlindOptions) { selectedBlind, expanded ->
            bigBlind = selectedBlind
            expandedBigBlind = expanded
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu déroulant pour "Position du joueur"
        Text(
            text = if (positionPlayer.isEmpty()) "Ma position" else "Je suis à : $positionPlayer",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedPosition = !expandedPosition }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expandedPosition,
            onDismissRequest = { expandedPosition = false }
        ) {
            positionOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        positionPlayer = option.toString()
                        expandedPosition = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (positionButton.isEmpty()) "Boutton" else "Boutton à : $positionButton",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedPositionButton = !expandedPositionButton }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expandedPositionButton,
            onDismissRequest = { expandedPositionButton = false }
        ) {
            positionButtonOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        positionButton = option.toString()
                        expandedPositionButton = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sélection des cartes de la main

        CardSelector(
            card1 = card1,
            card2 = card2,
            flop1 = flop1,
            flop2 = flop2,
            flop3 = flop3,
            turn = turn,
            river = river,
            cards = cards,
            expandedCard1 = expandedCard1,
            expandedCard2 = expandedCard2,
            expandedFlop1 = expandedFlop1,
            expandedFlop2 = expandedFlop2,
            expandedFlop3 = expandedFlop3,
            expandedTurn = expandedTurn,
            expandedRiver = expandedRiver,
            onCard1Selected = { card1 = it },
            onCard2Selected = { card2 = it },
            onFlop1Selected = { flop1 = it },
            onFlop2Selected = { flop2 = it },
            onFlop3Selected = { flop3 = it },
            onTurnSelected = { turn = it },
            onRiverSelected = { river = it },
            onExpandedCard1Changed = { expandedCard1 = it },
            onExpandedCard2Changed = { expandedCard2 = it },
            onExpandedFlop1Changed = { expandedFlop1 = it },
            onExpandedFlop2Changed = { expandedFlop2 = it },
            onExpandedFlop3Changed = { expandedFlop3 = it },
            onExpandedTurnChanged = { expandedTurn = it },
            onExpandedRiverChanged = { expandedRiver = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mode Selection
        ModeSelector(selectedMode, expandedMode, modes) { mode, expanded ->
            selectedMode = mode
            expandedMode = expanded
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour soumettre la requête
        AdviceButton(
            nbrPlayers = nbrPlayers,
            bigBlind = bigBlind,
            positionPlayer = positionPlayer,
            positionButton = positionButton,
            selectedMode = selectedMode,
            card1 = card1,
            card2 = card2,
            flop1 = flop1,
            flop2 = flop2,
            flop3 = flop3,
            turn = turn,
            river = river,
            playerInfo = playerInfo,
            myHistory = myHistory.value,
            myStack = "1000", // Remplacez par la valeur réelle de votre stack
            onAdviceReceived = { advice ->
                adviceText = advice
                showDialog = true
            }
        )

        if (showDialog) {
            AdviceDialog(adviceText) {
                showDialog = false
            }
        }
        // Player Info Dialog
        if (currentPlayerIndex != null) {
            PlayerInfoDialog(
                playerInfo = playerInfo.getOrPut(currentPlayerIndex!!) { PlayerInfo() },
                playerIndex = currentPlayerIndex!!,
                onDismiss = { updatedPlayerInfo ->
                    if (updatedPlayerInfo != null) {
                        playerInfo[currentPlayerIndex!!] = updatedPlayerInfo
                    }
                    currentPlayerIndex = null
                }
            )
        }
    }
}