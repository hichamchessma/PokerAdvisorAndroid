/*PokerAdvisorScreen*//*

package com.example.pokeradvisorapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokeradvisorapp.OpenAiResponse
import com.example.pokeradvisorapp.R
import com.example.pokeradvisorapp.RetrofitInstance
import com.example.pokeradvisorapp.ui.theme.Message
import com.example.pokeradvisorapp.ui.theme.OpenAiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.table_poker), // Table de poker
                contentDescription = "Table de poker",
                modifier = Modifier.fillMaxSize()
            )

            // Ajouter les icônes des joueurs pour les 9 positions
            val playerPositions = listOf(
                Modifier.offset(x = 255.dp, y = 15.dp),  // Position 1
                Modifier.offset(x = 330.dp, y = 45.dp),  // Position 2
                Modifier.offset(x = 330.dp, y = 120.dp), // Position 3
                Modifier.offset(x = 255.dp, y = 155.dp), // Position 4
                Modifier.offset(x = 180.dp, y = 155.dp), // Position 5
                Modifier.offset(x = 105.dp, y = 155.dp), // Position 6
                Modifier.offset(x = 30.dp, y = 120.dp),  // Position 7
                Modifier.offset(x = 30.dp, y = 45.dp),   // Position 8
                Modifier.offset(x = 100.dp, y = 15.dp)   // Position 9
            )

            playerPositions.forEachIndexed { index, positionModifier ->
                Box(modifier = positionModifier.clickable { currentPlayerIndex = index }) {
                    Image(
                        painter = painterResource(id = R.drawable.poker_player), // Icône du joueur
                        contentDescription = "Joueur",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
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
        Text(
            text = if (bigBlind.isEmpty()) "Valeur de la Big Blind (menu déroulant)" else "Big Blind : $bigBlind",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedBigBlind = true }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expandedBigBlind,
            onDismissRequest = { expandedBigBlind = false }
        ) {
            bigBlindOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        bigBlind = option
                        expandedBigBlind = false
                    }
                )
            }
        }

        // Editable Big Blind
        TextField(
            value = bigBlind,
            onValueChange = { newValue ->
                bigBlind = newValue
            },
            label = { Text("Entrer la valeur de la Big Blind") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sélection des cartes de la main
        Text(text = "Sélection des cartes de la main", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Carte 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Value Selector for Card 1
            Text(
                text = if (card1Value.isEmpty()) "Valeur 1" else card1Value,
                modifier = Modifier
                    .clickable { expandedCard1Value = true }
                    .padding(2.dp)
                    .weight(0.8f),
                fontSize = 12.sp,
                        textAlign = TextAlign.Center  // Centrer le texte
            )
            DropdownMenu(
                expanded = expandedCard1Value,
                onDismissRequest = { expandedCard1Value = false }
            ) {
                values.forEach { value ->
                    DropdownMenuItem(
                        text = { Text(text = value) },
                        onClick = {
                            card1Value = value
                            expandedCard1Value = false
                        }
                    )
                }
            }

            // Suit Selector for Card 1
            Text(
                text = if (card1Suit.isEmpty()) "Couleur 1" else card1Suit,
                modifier = Modifier
                    .clickable { expandedCard1Suit = true }
                    .padding(2.dp)
                    .weight(0.8f),
                fontSize = 12.sp
            )
            DropdownMenu(
                expanded = expandedCard1Suit,
                onDismissRequest = { expandedCard1Suit = false }
            ) {
                suits.forEach { suit ->
                    DropdownMenuItem(
                        text = { Text(text = suit) },
                        onClick = {
                            card1Suit = suit
                            expandedCard1Suit = false
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
            // Value Selector for Card 2
            Text(
                text = if (card2Value.isEmpty()) "Valeur 2" else card2Value,
                modifier = Modifier
                    .clickable { expandedCard2Value = true }
                    .padding(2.dp)
                    .weight(0.8f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            DropdownMenu(
                expanded = expandedCard2Value,
                onDismissRequest = { expandedCard2Value = false }
            ) {
                values.forEach { value ->
                    DropdownMenuItem(
                        text = { Text(text = value) },
                        onClick = {
                            card2Value = value
                            expandedCard2Value = false
                        }
                    )
                }
            }

            // Suit Selector for Card 2
            Text(
                text = if (card2Suit.isEmpty()) "Couleur 2" else card2Suit,
                modifier = Modifier
                    .clickable { expandedCard2Suit = true }
                    .padding(2.dp)
                    .weight(0.8f),
                fontSize = 12.sp
            )
            DropdownMenu(
                expanded = expandedCard2Suit,
                onDismissRequest = { expandedCard2Suit = false }
            ) {
                suits.forEach { suit ->
                    DropdownMenuItem(
                        text = { Text(text = suit) },
                        onClick = {
                            card2Suit = suit
                            expandedCard2Suit = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mode Selection
        Text(
            text = if (selectedMode.isEmpty()) "Mode de jeu" else "Mode de jeu : $selectedMode",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedMode = true }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(
            expanded = expandedMode,
            onDismissRequest = { expandedMode = false }
        ) {
            modes.forEach { mode ->
                DropdownMenuItem(
                    text = { Text(text = mode) },
                    onClick = {
                        selectedMode = mode
                        expandedMode = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nbrPlayers.isEmpty() || bigBlind.isEmpty() || position.isEmpty() || card1Suit.isEmpty() || card1Value.isEmpty() || card2Suit.isEmpty() || card2Value.isEmpty() || selectedMode.isEmpty()) {
                    Toast.makeText(context, "Veuillez remplir tous les champs avant de soumettre.", Toast.LENGTH_LONG).show()
                } else {
                    val messages = listOf(
                        Message(role = "user", content = """
                    Nombre de joueurs : $nbrPlayers
                    Big Blind : $bigBlind
                    Position : $position
                    Carte 1 : $card1Value de $card1Suit
                    Carte 2 : $card2Value de $card2Suit
                    Mode de jeu : $selectedMode
                """.trimIndent())
                    )

                    val request = OpenAiRequest(
                        model = "gpt-4",
                        messages = messages
                    )

                    val openAiApiService = RetrofitInstance.openAiApiService
                    openAiApiService.getChatCompletion(request).enqueue(object : Callback<OpenAiResponse> {
                        override fun onResponse(call: Call<OpenAiResponse>, response: Response<OpenAiResponse>) {
                            if (response.isSuccessful) {
                                val advice = response.body()?.choices?.firstOrNull()?.message?.content
                                if (advice != null) {
                                    adviceText = advice
                                    showDialog = true
                                } else {
                                    Toast.makeText(context, "Erreur: Réponse vide", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(context, "Erreur: ${response.code()} - ${response.message()}", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<OpenAiResponse>, t: Throwable) {
                            Toast.makeText(context, "Échec de la requête : ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Soumettre", color = MaterialTheme.colorScheme.onPrimary)
        }

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



@Composable
fun PlayerInfoDialog(playerInfo: PlayerInfo, onDismiss: (PlayerInfo?) -> Unit) {
    var stack by remember { mutableStateOf(playerInfo.stack) }
    var lastAction by remember { mutableStateOf("Fold") } // Fold par défaut
    var actualMood by remember { mutableStateOf(playerInfo.actualMood) }
    var bouton by remember { mutableStateOf(playerInfo.bouton) }

    val actions = listOf("Check", "Call", "Raise", "All in")
    var expanded by remember { mutableStateOf(false) }
    var raiseAmount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss(null) },
        confirmButton = {
            Button(onClick = { onDismiss(PlayerInfo(stack, lastAction, actualMood, bouton)) }) {
                Text("Confirmer")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss(null) }) {
                Text("Annuler")
            }
        },
        title = { Text(text = "Informations sur le joueur") },
        text = {
            Column {
                TextField(
                    value = stack,
                    onValueChange = { stack = it },
                    label = { Text("Stack") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Menu déroulant pour "Dernière action"
                Text(
                    text = "Dernière action : $lastAction",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                        .padding(vertical = 8.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    actions.forEach { action ->
                        DropdownMenuItem(
                            text = { Text(text = action) },
                            onClick = {
                                lastAction = action
                                expanded = false
                            }
                        )
                    }
                }

                // Champ pour entrer le montant du bet si "Raise" est sélectionné
                if (lastAction == "Raise") {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = raiseAmount,
                        onValueChange = { raiseAmount = it },
                        label = { Text("Montant du pari") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = actualMood,
                    onValueChange = { actualMood = it },
                    label = { Text("Humeur actuelle") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Bouton")
                    Switch(checked = bouton, onCheckedChange = { bouton = it })
                }
            }
        }
    )
}

data class PlayerInfo(
    var stack: String = "",
    var lastAction: String = "",
    var actualMood: String = "",
    var bouton: Boolean = false
)

data class TableInfo(
    val playersNumber: Int,
    val blinds: String,
    val hand: String,
    val step: String,
    val buttonPosition: Int,
    val myPosition: Int,
    val myStack: String
)

data class PlayerInfoDetails(
    val stack: String,
    val action: String,
    val mood: String,
    val historyActions: List<String>
)
*/
