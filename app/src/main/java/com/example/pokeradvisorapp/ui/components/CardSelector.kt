/*PokerAdvisorScreen*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CardSelector(
    card1Suit: String,
    card1Value: String,
    card2Suit: String,
    card2Value: String,
    suits: List<String>,
    values: List<String>,
    expandedCard1Suit: Boolean,
    expandedCard1Value: Boolean,
    expandedCard2Suit: Boolean,
    expandedCard2Value: Boolean,
    onCard1SuitSelected: (String) -> Unit,
    onCard1ValueSelected: (String) -> Unit,
    onCard2SuitSelected: (String) -> Unit,
    onCard2ValueSelected: (String) -> Unit,
    onExpandedCard1SuitChanged: (Boolean) -> Unit,
    onExpandedCard1ValueChanged: (Boolean) -> Unit,
    onExpandedCard2SuitChanged: (Boolean) -> Unit,
    onExpandedCard2ValueChanged: (Boolean) -> Unit
) {
    // Carte 1
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Valeur 1
        Text(
            text = if (card1Value.isEmpty()) "Valeur 1" else card1Value,
            modifier = Modifier
                .clickable { onExpandedCard1ValueChanged(true) }
                .padding(2.dp)
                .weight(0.8f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        DropdownMenu(
            expanded = expandedCard1Value,
            onDismissRequest = { onExpandedCard1ValueChanged(false) }
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    text = { Text(text = value) },
                    onClick = {
                        onCard1ValueSelected(value)
                        onExpandedCard1ValueChanged(false)
                    }
                )
            }
        }

        // Couleur 1
        Text(
            text = if (card1Suit.isEmpty()) "Couleur 1" else card1Suit,
            modifier = Modifier
                .clickable { onExpandedCard1SuitChanged(true) }
                .padding(2.dp)
                .weight(0.8f),
            fontSize = 12.sp
        )
        DropdownMenu(
            expanded = expandedCard1Suit,
            onDismissRequest = { onExpandedCard1SuitChanged(false) }
        ) {
            suits.forEach { suit ->
                DropdownMenuItem(
                    text = { Text(text = suit) },
                    onClick = {
                        onCard1SuitSelected(suit)
                        onExpandedCard1SuitChanged(false)
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Carte 2
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Valeur 2
        Text(
            text = if (card2Value.isEmpty()) "Valeur 2" else card2Value,
            modifier = Modifier
                .clickable { onExpandedCard2ValueChanged(true) }
                .padding(2.dp)
                .weight(0.8f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        DropdownMenu(
            expanded = expandedCard2Value,
            onDismissRequest = { onExpandedCard2ValueChanged(false) }
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    text = { Text(text = value) },
                    onClick = {
                        onCard2ValueSelected(value)
                        onExpandedCard2ValueChanged(false)
                    }
                )
            }
        }

        // Couleur 2
        Text(
            text = if (card2Suit.isEmpty()) "Couleur 2" else card2Suit,
            modifier = Modifier
                .clickable { onExpandedCard2SuitChanged(true) }
                .padding(2.dp)
                .weight(0.8f),
            fontSize = 12.sp
        )
        DropdownMenu(
            expanded = expandedCard2Suit,
            onDismissRequest = { onExpandedCard2SuitChanged(false) }
        ) {
            suits.forEach { suit ->
                DropdownMenuItem(
                    text = { Text(text = suit) },
                    onClick = {
                        onCard2SuitSelected(suit)
                        onExpandedCard2SuitChanged(false)
                    }
                )
            }
        }
    }
}
