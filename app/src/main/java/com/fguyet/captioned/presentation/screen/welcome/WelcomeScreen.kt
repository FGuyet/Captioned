package com.fguyet.captioned.presentation.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.core.designsystem.CaptionedScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(
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

    CaptionedScreen {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // TODO add animation
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.app_name)
            )
            WelcomeHeadlines()
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { welcomeViewModel.login() }
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
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
internal fun WelcomeScreenPreview() {
    WelcomeScreen(
        onSignedIn = {}
    )
}

