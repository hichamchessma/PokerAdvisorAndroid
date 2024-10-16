/*PokerTableComponent*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokeradvisorapp.R

@Composable
fun PokerTableComponent(
    playerInfo: MutableMap<Int, PlayerInfo>,
    currentPlayerIndex: Int?,
    onPlayerClick: (Int) -> Unit,
    myPositionIndex: Int?,
    buttonPosition: Int? // Position du bouton Dealer
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Afficher la table de poker
        Image(
            painter = painterResource(id = R.drawable.table_poker),
            contentDescription = "Table de poker",
            modifier = Modifier.fillMaxSize()
        )

        // Liste des positions des joueurs sur la table
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

        // Liste des positions du bouton Dealer
        val buttonPositions = listOf(
            Modifier.offset(x = 255.dp, y = 55.dp),  // Position 1
            Modifier.offset(x = 305.dp, y = 70.dp),  // Position 2
            Modifier.offset(x = 305.dp, y = 105.dp),  // Position 3
            Modifier.offset(x = 255.dp, y = 125.dp), // Position 4
            Modifier.offset(x = 180.dp, y = 125.dp), // Position 5
            Modifier.offset(x = 105.dp, y = 125.dp), // Position 6
            Modifier.offset(x = 55.dp, y = 105.dp),   // Position 7
            Modifier.offset(x = 55.dp, y = 70.dp),   // Position 8
            Modifier.offset(x = 105.dp, y = 55.dp)   // Position 9
        )

        // Parcours de chaque position pour afficher les joueurs
        playerPositions.forEachIndexed { index, positionModifier ->
            Box(modifier = positionModifier.clickable { onPlayerClick(index) }) {
                // Afficher l'icône du joueur actif ou l'icône par défaut
                val playerIcon = if (myPositionIndex?.minus(1) == index) {
                    R.drawable.poker_player_me // Icône du joueur actif

                } else {
                    R.drawable.poker_player // Icône par défaut du joueur
                }
                Image(
                    painter = painterResource(id = playerIcon),
                    contentDescription = "Joueur",
                    modifier = Modifier.size(24.dp),
                    colorFilter = if (myPositionIndex?.minus(1) == index) {
                        ColorFilter.lighting(Color.Cyan, Color.Black) // Appliquer une teinte de couleur uniquement au joueur actif
                    } else {
                        null // Ne pas appliquer de filtre pour les autres joueurs
                    }
                )
            }
        }

        // Afficher l'icône du bouton Dealer si la position du bouton est définie
        buttonPosition?.let {
            Box(modifier = buttonPositions[it-1]) {
                Image(
                    painter = painterResource(id = R.drawable.poker_dealer),
                    contentDescription = "Dealer",
                    modifier = Modifier
                        .size(20.dp) // Taille réduite de l'image du bouton Dealer
                        .clip(CircleShape)
                )
            }
        }
    }
}
