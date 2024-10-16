/*PokerAdvisorScreen*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BigBlindSelector(
    bigBlind: String,
    expandedBigBlind: Boolean,
    bigBlindOptions: List<String>,
    onBigBlindSelected: (String, Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(expandedBigBlind) }

    Text(
        text = if (bigBlind.isEmpty()) "Valeur de la Big Blind" else "Big Blind : $bigBlind",
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 8.dp)
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        bigBlindOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option) },
                onClick = {
                    onBigBlindSelected(option, false)
                }
            )
        }
    }

    TextField(
        value = bigBlind,
        onValueChange = { newValue ->
            onBigBlindSelected(newValue, expanded)
        },
        label = { Text("Entrer la valeur de la Big Blind") },
        modifier = Modifier.fillMaxWidth()
    )
}
