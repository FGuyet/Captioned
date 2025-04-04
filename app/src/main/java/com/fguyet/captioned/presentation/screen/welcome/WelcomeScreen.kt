package com.fguyet.captioned.presentation.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.core.designsystem.CaptionedButtonConfig
import com.fguyet.captioned.core.designsystem.CaptionedScreen

@Composable
internal fun WelcomeScreen(
    modifier: Modifier = Modifier,
    uiState: WelcomeUiState,
    onSignIn: () -> Unit,
) {
    CaptionedScreen(
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                // TODO add animation
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopCenter),
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.app_logo),
                    contentDescription = stringResource(R.string.app_name)
                )
                WelcomeHeadlines(modifier = Modifier.align(Alignment.Center))
            }
        },
        actionButtonConfig = CaptionedButtonConfig(
            text = stringResource(R.string.sign_in),
            onClick = onSignIn,
            enabled = !uiState.isLoading,
        ),
        isLoading = uiState.isLoading,
    )
}

@Composable
private fun WelcomeHeadlines(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.welcome_to_captioned),
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = stringResource(R.string.caption_welcome_brief),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        modifier = Modifier,
        uiState = WelcomeUiState(
            isLoading = false,
            isLoggedIn = false,
        ),
        onSignIn = {}
    )
}

