/*AdviceButton*/
package com.example.pokeradvisorapp.ui.components

import android.util.Log
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
    positionPlayer: String,
    positionButton: String,
    selectedMode: String,
    card1: String,
    card2: String,
    flop1: String,
    flop2: String,
    flop3: String,
    turn: String,
    river: String,
    playerInfo: Map<Int, PlayerInfo>,
    myHistory: List<String>,
    myStack: String,
    onAdviceReceived: (String) -> Unit
) {
    val context = LocalContext.current

    Button(
        onClick = {
            if (nbrPlayers.isEmpty() || bigBlind.isEmpty() || positionPlayer.isEmpty() || positionButton.isEmpty()) {
                Toast.makeText(context, "Veuillez remplir tous les champs avant de soumettre.", Toast.LENGTH_LONG).show()
            } else {
                // Construire les informations de la table (ti)
                val board = listOf(flop1, flop2, flop3, turn, river).filter { it.isNotEmpty() }.joinToString("")
                val tableInfo = "ti($nbrPlayers,$bigBlind,${card1}${card2},$positionButton,$positionPlayer,$myStack,$board)"

                // Construire les informations des joueurs (pi)
                val playersInfo = playerInfo.filter { it.value.inOut } // Filtrer les joueurs qui sont "in"
                    .map { (index, info) ->
                        if (index  == positionPlayer.toIntOrNull()) {
                            // Si c'est ma position, utiliser "moi" au lieu de "pX"
                            val actionsHistory = info.historyActions.joinToString(",")
                            "moi(${info.stack},${info.lastAction},${info.actualMood},($actionsHistory))"
                        } else {
                            // Pour les autres joueurs
                            val playerId = "p${index }"
                            val actionsHistory = info.historyActions.joinToString(",")
                            "$playerId(${info.stack},${info.lastAction},${info.actualMood},($actionsHistory))"
                        }
                    }.joinToString(" ")

                // Construire les informations de mon historique (moi)
                val myActions = myHistory.joinToString(",")
                val myInfo = "moi($myActions)"

                // Assembler la requête complète
                val requeststructure="voici le format table infos avec 9joueurs  ti(joueurs encore dans le coup,bigBlind,mes cartes,positionButton,positionPlayer,myStack,board)" +
                        "\n players infos pi(stack,lastAction,actualMood,(actionsHistory)) "
                val requestString = "$tableInfo $playersInfo "

                // Ajouter les instructions pour le format de réponse attendu
                val responseFormatInstructions = "réponds dans ce format en suivant GTO: {t (sec)\nacting (nerveux/calme/je-m'en-foutiste)\nAction: B+montant/F/CH/C }"

                val messages = listOf(
                    Message(role = "user", content = "$requeststructure\n$requestString\n\n$responseFormatInstructions")
                )

                val request = OpenAiRequest(
                    model = "gpt-4",
                    messages = messages
                )

                // Loguer la requête dans la console
                Log.d("AdviceButton", "Request to GPT-4: $requeststructure\n$requestString\n\n$responseFormatInstructions"
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
