package ru.mareanexx.travelogue.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mareanexx.travelogue.presentation.navigation.AppNavHost
import ru.mareanexx.travelogue.presentation.theme.TravelogueTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelogueTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}