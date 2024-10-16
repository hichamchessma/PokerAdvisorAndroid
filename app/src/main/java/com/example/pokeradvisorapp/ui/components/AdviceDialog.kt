/*AdviceDialog*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdviceDialog(adviceText: String, onDismiss: () -> Unit) {
    val scrollState = rememberScrollState()
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Fermer")
            }
        },
        title = {
            Text(text = "Conseil de Poker")
        },
        text = {
            Text(
                text = adviceText,
                modifier = Modifier
                    .height(300.dp) // Limiter la hauteur du texte
                    .verticalScroll(scrollState)
            )
        }
    )
}