package com.fguyet.captioned.presentation.screen.welcome

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.fguyet.captioned.R
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
                context.getString(R.string.you_are_logged_in),
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
