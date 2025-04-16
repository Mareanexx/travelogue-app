package ru.mareanexx.travelogue.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.start.components.ButtonsColumn
import ru.mareanexx.travelogue.presentation.screens.start.components.LogoAndSloganTextColumn
import ru.mareanexx.travelogue.presentation.screens.start.components.login.LoginForm
import ru.mareanexx.travelogue.presentation.screens.start.components.register.RegisterForm

enum class SheetType {
    None, Login, Register, Profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(onNavigateToProfile: () -> Unit) {
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
                    onNavigateToProfile()
                })
                SheetType.Register -> RegisterForm(onOpenProfileCreatePanel = { sheetType.value = SheetType.Profile })
                SheetType.Profile -> Spacer(modifier = Modifier) // TODO() ProfileForm()
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