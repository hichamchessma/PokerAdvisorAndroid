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

    var position by remember { mutableStateOf("") }
    var expandedPosition by remember { mutableStateOf(false) }
    val positionOptions = (1..9).toList()

    // Card Selection States
    var card1Suit by remember { mutableStateOf("") }
    var card1Value by remember { mutableStateOf("") }
    var card2Suit by remember { mutableStateOf("") }
    var card2Value by remember { mutableStateOf("") }

    val suits = listOf("♠", "♥", "♦", "♣")
    val values = listOf("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2")

    var expandedCard1Suit by remember { mutableStateOf(false) }
    var expandedCard1Value by remember { mutableStateOf(false) }
    var expandedCard2Suit by remember { mutableStateOf(false) }
    var expandedCard2Value by remember { mutableStateOf(false) }

    val modes = listOf("Agressif", "Passif", "Adaptatif")
    var selectedMode by remember { mutableStateOf("") }
    var expandedMode by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var adviceText by remember { mutableStateOf("") }

    // State for tracking player-specific information
    val playerInfo = remember { mutableStateMapOf<Int, PlayerInfo>() }
    var currentPlayerIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Table de poker avec les joueurs
        PokerTableComponent(playerInfo, currentPlayerIndex) {
            currentPlayerIndex = it
        }

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
            text = if (position.isEmpty()) "Position du joueur" else "Position : $position",
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
                        position = option.toString()
                        expandedPosition = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sélection des cartes de la main
        CardSelector(card1Suit, card1Value, card2Suit, card2Value, suits, values,
            expandedCard1Suit, expandedCard1Value, expandedCard2Suit, expandedCard2Value,
            onCard1SuitSelected = { suit -> card1Suit = suit },
            onCard1ValueSelected = { value -> card1Value = value },
            onCard2SuitSelected = { suit -> card2Suit = suit },
            onCard2ValueSelected = { value -> card2Value = value },
            onExpandedCard1SuitChanged = { expandedCard1Suit = it },
            onExpandedCard1ValueChanged = { expandedCard1Value = it },
            onExpandedCard2SuitChanged = { expandedCard2Suit = it },
            onExpandedCard2ValueChanged = { expandedCard2Value = it }
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
            position = position,
            selectedMode = selectedMode,
            card1Suit = card1Suit,
            card1Value = card1Value,
            card2Suit = card2Suit,
            card2Value = card2Value,
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