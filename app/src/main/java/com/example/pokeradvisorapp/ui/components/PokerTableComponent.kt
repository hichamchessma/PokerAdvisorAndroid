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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokeradvisorapp.R

@Composable
fun PokerTableComponent(
    playerInfo: MutableMap<Int, PlayerInfo>,
    currentPlayerIndex: Int?,
    onPlayerClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.table_poker),
            contentDescription = "Table de poker",
            modifier = Modifier.fillMaxSize()
        )

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
            Box(modifier = positionModifier.clickable { onPlayerClick(index) }) {
                Image(
                    painter = painterResource(id = R.drawable.poker_player),
                    contentDescription = "Joueur",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
