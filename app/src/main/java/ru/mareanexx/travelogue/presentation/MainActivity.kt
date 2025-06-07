package ru.mareanexx.travelogue.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mareanexx.travelogue.presentation.navigation.AppNavHost
import ru.mareanexx.common.ui.theme.TravelogueTheme
import ru.mareanexx.common.utils.UserSessionManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userSessionManager: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelogueTheme {
                val rootNavController = rememberNavController()
                var startDestination by remember { mutableStateOf<String>("") }

                LaunchedEffect(Unit) {
                    val profileId = userSessionManager.getProfileId()
                    startDestination = if (profileId != -1) "main" else "auth"
                }

                if (startDestination != "") {
                    AppNavHost(
                        rootNavController = rootNavController,
                        startDestination = startDestination
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = Color.Black) }
                }
            }
        }
    }
}