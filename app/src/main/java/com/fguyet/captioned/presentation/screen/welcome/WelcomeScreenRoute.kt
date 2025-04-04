package com.fguyet.captioned.presentation.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreenRoute(
    modifier: Modifier = Modifier,
    onSignedIn: () -> Unit,
    welcomeViewModel: WelcomeViewModel = koinViewModel()
) {
    val uiState by welcomeViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onSignedIn()
        }
    }

    WelcomeScreen(
        modifier = modifier,
        uiState = uiState,
        onSignIn = { welcomeViewModel.login() },
    )
}
