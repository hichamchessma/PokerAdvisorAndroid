/*MainActivity*/
package com.example.pokeradvisorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokeradvisorapp.ui.components.PokerAdvisorScreen
import com.example.pokeradvisorapp.ui.theme.PokerAdvisorAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerAdvisorAppTheme {
                PokerAdvisorScreen() // Appelle la fonction composable principale
                MainViewWithBackground()
            }
        }
    }
}

@Composable
fun MainViewWithBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Ajouter l'image d'arrière-plan
        Image(
            painter = painterResource(id = R.drawable.background), // Remplacez par le nom de votre ressource drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f // Transparence légère pour que le contenu principal soit bien visible
        )

        // Votre contenu principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

        }
    }
}
