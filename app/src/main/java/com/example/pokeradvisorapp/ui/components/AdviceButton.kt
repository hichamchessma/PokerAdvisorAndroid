    /*AdviceButton*/
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
        card1: String,
        card2: String,
        flop1: String,
        flop2: String,
        flop3: String,
        turn: String,
        river: String,
        onAdviceReceived: (String) -> Unit
    ) {
        val context = LocalContext.current

        Button(
            onClick = {
                if (nbrPlayers.isEmpty() /*|| bigBlind.isEmpty() || position.isEmpty() || selectedMode.isEmpty()
                */) {
                    Toast.makeText(context, "Veuillez remplir tous les champs avant de soumettre.", Toast.LENGTH_LONG).show()
                } else {
                    val messages = listOf(
                        Message(
                            role = "user", content = """
                            Nombre de joueurs : $nbrPlayers
                            Big Blind : $bigBlind
                            Position : $position
                            Carte 1 : $card1
                            Carte 2 : $card2
                            Flop 1 : $flop1
                            Flop 2 : $flop2
                            Flop 3 : $flop3
                            Turn : $turn
                            River : $river
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
