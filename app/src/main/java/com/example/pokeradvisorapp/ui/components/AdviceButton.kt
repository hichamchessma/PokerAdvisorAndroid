
package com.example.pokeradvisorapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.pokeradvisorapp.OpenAiResponse
import com.example.pokeradvisorapp.RetrofitInstance
import com.example.pokeradvisorapp.ui.theme.Message
import com.example.pokeradvisorapp.ui.theme.OpenAiRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AdviceButton(
    nbrPlayers: String,
    bigBlind: String,
    position: String,
    selectedMode: String,
    card1Suit: String,
    card1Value: String,
    card2Suit: String,
    card2Value: String,
    onAdviceReceived: (String) -> Unit
) {
    val context = LocalContext.current

    Button(
        onClick = {
            if (nbrPlayers.isEmpty() /*|| bigBlind.isEmpty() || position.isEmpty() || card1Suit.isEmpty() ||
                card1Value.isEmpty() || card2Suit.isEmpty() || card2Value.isEmpty() || selectedMode.isEmpty()
            */) {
                Toast.makeText(context, "Veuillez remplir tous les champs avant de soumettre.", Toast.LENGTH_LONG).show()
            } else {
                val messages = listOf(
                    Message(
                        role = "user", content = """
                        Nombre de joueurs : $nbrPlayers
                        Big Blind : $bigBlind
                        Position : $position
                        Carte 1 : $card1Value de $card1Suit
                        Carte 2 : $card2Value de $card2Suit
                        Mode de jeu : $selectedMode
                    """.trimIndent()
                    )
                )

                val request = OpenAiRequest(
                    model = "gpt-4",
                    messages = messages
                )

                // Utilisation des coroutines pour envoyer la requête en arrière-plan
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitInstance.openAiApiService.getChatCompletion(request).execute()
                        if (response.isSuccessful) {
                            response.body()?.choices?.firstOrNull()?.message?.content?.let { advice ->
                                withContext(Dispatchers.Main) {
                                    onAdviceReceived(advice)
                                }
                            } ?: withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Erreur: Réponse vide", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Erreur: ${response.code()} - ${response.message()}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Échec de la requête : ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Soumettre", color = MaterialTheme.colorScheme.onPrimary)
    }
}
