/*PokerTableComponent*/
package com.example.pokeradvisorapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    buttonPosition: Int?
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
            Modifier.offset(x = 255.dp, y = 15.dp),
            Modifier.offset(x = 330.dp, y = 45.dp),
            Modifier.offset(x = 330.dp, y = 120.dp),
            Modifier.offset(x = 255.dp, y = 155.dp),
            Modifier.offset(x = 180.dp, y = 155.dp),
            Modifier.offset(x = 105.dp, y = 155.dp),
            Modifier.offset(x = 30.dp, y = 120.dp),
            Modifier.offset(x = 30.dp, y = 45.dp),
            Modifier.offset(x = 100.dp, y = 15.dp)
        )

        val buttonPositions = listOf(
            Modifier.offset(x = 255.dp, y = 55.dp),
            Modifier.offset(x = 305.dp, y = 70.dp),
            Modifier.offset(x = 305.dp, y = 105.dp),
            Modifier.offset(x = 255.dp, y = 125.dp),
            Modifier.offset(x = 180.dp, y = 125.dp),
            Modifier.offset(x = 105.dp, y = 125.dp),
            Modifier.offset(x = 55.dp, y = 105.dp),
            Modifier.offset(x = 55.dp, y = 70.dp),
            Modifier.offset(x = 105.dp, y = 55.dp)
        )

        playerPositions.forEachIndexed { index, positionModifier ->
            val player = playerInfo[index+1]
            if (player != null && player.inOut) {
                Box(modifier = positionModifier.clickable { onPlayerClick(index + 1) }) {

                    val playerIcon = if (myPositionIndex == index + 1) {
                        R.drawable.poker_player_me

                    } else {
                        R.drawable.poker_player
                    }
                    if (index + 1 in listOf(8, 9, 1, 2)){
                        Text(
                            text = player.stack,
                            color = Color.White,
                            modifier = Modifier
                                .offset(y = -30.dp)
                                .then(if (player.stack.isNotEmpty())
                                    Modifier.background(Color.Black.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(50)
                                    ) else Modifier)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                } else {
                        Text(
                            text = player.stack,
                            color = Color.White,
                            modifier = Modifier
                                .offset(y = 30.dp)
                                .then(if (player.stack.isNotEmpty())
                                    Modifier.background(Color.Black.copy(alpha = 0.7f),
                                        shape = RoundedCornerShape(50)
                                    ) else Modifier)
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                }

                    Image(
                        painter = painterResource(id = playerIcon),
                        contentDescription = "Joueur",
                        modifier = Modifier.size(24.dp),
                        colorFilter = if (myPositionIndex == index + 1) {
                            ColorFilter.lighting(
                                Color.Cyan,
                                Color.Black
                            )
                        } else {
                            null
                        }
                    )
                }
            }
        }


        buttonPosition?.let {
            Box(modifier = buttonPositions[it-1]) {
                Image(
                    painter = painterResource(id = R.drawable.poker_dealer),
                    contentDescription = "Dealer",
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}
