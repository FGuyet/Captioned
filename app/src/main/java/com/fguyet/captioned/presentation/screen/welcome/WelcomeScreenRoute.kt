package com.fguyet.captioned.presentation.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun WelcomeScreenRoute(
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit,
) {
    WelcomeScreen(
        modifier = modifier,
        onSignedIn = onSignIn,
    )
}
