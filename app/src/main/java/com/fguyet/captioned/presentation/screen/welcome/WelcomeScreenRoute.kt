package com.fguyet.captioned.presentation.screen.welcome

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreenRoute(
    modifier: Modifier = Modifier,
    onSignedIn: () -> Unit,
    welcomeViewModel: WelcomeViewModel = koinViewModel()
) {
    val uiState by welcomeViewModel.uiState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            Toast.makeText(
                context,
                "You are logged in! (mocked action)",
                Toast.LENGTH_SHORT
            ).show()
            onSignedIn()
        }
    }

    WelcomeScreen(
        modifier = modifier,
        uiState = uiState,
        onSignIn = {
            // TODO real sign in
            welcomeViewModel.register()
        },
    )
}
