/*MainActivity*/
package com.example.pokeradvisorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pokeradvisorapp.ui.components.PokerAdvisorScreen
import com.example.pokeradvisorapp.ui.theme.PokerAdvisorAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerAdvisorAppTheme {
                PokerAdvisorScreen() // Appelle la fonction composable principale
            }
        }
    }
}
