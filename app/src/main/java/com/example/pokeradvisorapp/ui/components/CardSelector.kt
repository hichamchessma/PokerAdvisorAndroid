/*CardSelector*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CardSelector(
    card1: String,
    card2: String,
    flop1: String,
    flop2: String,
    flop3: String,
    turn: String,
    river: String,
    cards: List<String>,
    expandedCard1: Boolean,
    expandedCard2: Boolean,
    expandedFlop1: Boolean,
    expandedFlop2: Boolean,
    expandedFlop3: Boolean,
    expandedTurn: Boolean,
    expandedRiver: Boolean,
    onCard1Selected: (String) -> Unit,
    onCard2Selected: (String) -> Unit,
    onFlop1Selected: (String) -> Unit,
    onFlop2Selected: (String) -> Unit,
    onFlop3Selected: (String) -> Unit,
    onTurnSelected: (String) -> Unit,
    onRiverSelected: (String) -> Unit,
    onExpandedCard1Changed: (Boolean) -> Unit,
    onExpandedCard2Changed: (Boolean) -> Unit,
    onExpandedFlop1Changed: (Boolean) -> Unit,
    onExpandedFlop2Changed: (Boolean) -> Unit,
    onExpandedFlop3Changed: (Boolean) -> Unit,
    onExpandedTurnChanged: (Boolean) -> Unit,
    onExpandedRiverChanged: (Boolean) -> Unit
)  {
    Row(
        modifier = Modifier
            .width(150.dp)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp), // Réduire l'espacement entre les cartes
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Carte 1
        Text(
            text = if (card1.isEmpty()) "Carte 1" else "Carte 1 : $card1",
            modifier = Modifier
                .weight(0.45f)
                .clickable { onExpandedCard1Changed(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedCard1,
            onDismissRequest = { onExpandedCard1Changed(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onCard1Selected(card)
                        onExpandedCard1Changed(false)
                    }
                )
            }
        }



        // Carte 2
        Text(
            text = if (card2.isEmpty()) "Carte 2" else "Carte 2 : $card2",
            modifier = Modifier
                .weight(0.45f)
                .clickable { onExpandedCard2Changed(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedCard2,
            onDismissRequest = { onExpandedCard2Changed(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onCard2Selected(card)
                        onExpandedCard2Changed(false)
                    }
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (flop1.isEmpty()) "Flop1" else "Flop1 : $flop1",
            modifier = Modifier
                .width(70.dp)
                .clickable { onExpandedFlop1Changed(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedFlop1,
            onDismissRequest = { onExpandedFlop1Changed(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onFlop1Selected(card)
                        onExpandedFlop1Changed(false)
                    }
                )
            }
        }

        Text(
            text = if (flop2.isEmpty()) "Flop2" else "Flop2 : $flop2",
            modifier = Modifier
                .width(70.dp)
                .clickable { onExpandedFlop2Changed(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedFlop2,
            onDismissRequest = { onExpandedFlop2Changed(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onFlop2Selected(card)
                        onExpandedFlop2Changed(false)
                    }
                )
            }
        }

        Text(
            text = if (flop3.isEmpty()) "Flop3" else "Flop3 : $flop3",
            modifier = Modifier
                .width(70.dp)
                .clickable { onExpandedFlop3Changed(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedFlop3,
            onDismissRequest = { onExpandedFlop3Changed(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onFlop3Selected(card)
                        onExpandedFlop3Changed(false)
                    }
                )
            }
        }

        Text(
            text = if (turn.isEmpty()) "Turn" else "Turn : $turn",
            modifier = Modifier
                .width(70.dp)
                .clickable { onExpandedTurnChanged(true) }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedTurn,
            onDismissRequest = { onExpandedTurnChanged(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onTurnSelected(card)
                        onExpandedTurnChanged(false)
                    }
                )
            }
        }

        Text(
            text = if (river.isEmpty()) "River" else "River : $river",
            modifier = Modifier
                .width(70.dp)
                .clickable { onExpandedRiverChanged(true)  }
                .padding(4.dp)
        )
        DropdownMenu(
            expanded = expandedRiver,
            onDismissRequest = { onExpandedRiverChanged(false) }
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    text = { Text(text = card) },
                    onClick = {
                        onRiverSelected(card)
                        onExpandedRiverChanged(false)
                    }
                )
            }
        }
    }

}

