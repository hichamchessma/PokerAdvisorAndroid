/*ModeSelector*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModeSelector(
    selectedMode: String,
    expandedMode: Boolean,
    modes: List<String>,
    onModeSelected: (String, Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(expandedMode) }

    Text(
        text = if (selectedMode.isEmpty()) "Mode de jeu" else "Mode de jeu : $selectedMode",
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 8.dp)
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        modes.forEach { mode ->
            DropdownMenuItem(
                text = { Text(text = mode) },
                onClick = {
                    onModeSelected(mode, false)
                }
            )
        }
    }
}
