package ru.mareanexx.feature_auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.components.ButtonsColumn
import ru.mareanexx.feature_auth.presentation.components.LogoAndSloganTextColumn
import ru.mareanexx.feature_auth.presentation.screens.create_profile.components.ProfileForm
import ru.mareanexx.feature_auth.presentation.screens.login.components.LoginForm
import ru.mareanexx.feature_auth.presentation.screens.register.components.RegisterForm

enum class SheetType {
    None, Login, Register, Profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(onNavigateToMain: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetType = remember { mutableStateOf(SheetType.None) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.start_screen_back),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        ButtonsColumn(
            modifier = Modifier.align(Alignment.BottomCenter),
            onOpenRegisterPanel = {
                sheetType.value = SheetType.Register
                showBottomSheet.value = true
            },
            onOpenLoginPanel = {
                sheetType.value = SheetType.Login
                showBottomSheet.value = true
            }
        )
        LogoAndSloganTextColumn()
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            containerColor = Color.White,
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet.value = false }
        ) {
            when(sheetType.value) {
                SheetType.Login -> LoginForm(onLoginSuccess = {
                    showBottomSheet.value = false
                    onNavigateToMain()
                })
                SheetType.Register -> RegisterForm(
                    onOpenProfileCreatePanel = { sheetType.value = SheetType.Profile }
                )
                SheetType.Profile -> ProfileForm(
                    onSuccessfulProfileCreation = {
                        showBottomSheet.value = false
                        onNavigateToMain()
                    }
                )
                SheetType.None -> {}
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewStartScreen() {
    StartScreen {}
}