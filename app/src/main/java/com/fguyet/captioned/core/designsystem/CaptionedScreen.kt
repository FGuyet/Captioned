package com.fguyet.captioned.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CaptionedScreen(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
    actionButtonConfig: CaptionedButtonConfig? = null,
    // TODO display loading indicator on screen components instead of full screen
    isLoading: Boolean = false,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .let {
                if (isLoading) it.shimmer() else it
            },
        content = { scaffoldPaddingValues ->
            val contentPaddingValues = PaddingValues(
                top = 16.dp + scaffoldPaddingValues.calculateTopPadding(),
                bottom = 16.dp + scaffoldPaddingValues.calculateBottomPadding(),
                start = 16.dp + scaffoldPaddingValues.calculateStartPadding(LocalLayoutDirection.current),
                end = 16.dp + scaffoldPaddingValues.calculateEndPadding(LocalLayoutDirection.current)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                content(contentPaddingValues)

                if (actionButtonConfig != null) {
                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 16.dp)
                            .padding(contentPaddingValues),
                        colors = ButtonDefaults.buttonColors(),
                        onClick = actionButtonConfig.onClick,
                        enabled = actionButtonConfig.enabled
                    ) {
                        Text(
                            text = actionButtonConfig.text,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
        },
    )
}

@Preview
@Composable
internal fun CaptionScreenPreview() {
    CaptionedScreen(
        content = {
            Text(
                text = "Hello world",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actionButtonConfig = CaptionedButtonConfig(
            text = "Click me",
            onClick = {},
        )
    )
}


data class CaptionedButtonConfig(
    val text: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
)