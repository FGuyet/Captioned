package com.fguyet.captioned.presentation.screen.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CaptureScreenRoute(
    modifier: Modifier = Modifier,
    onCaptureCompleted: () -> Unit,
    onCaptureCancelled: () -> Unit,
    captureViewModel: CaptureViewModel = koinViewModel()
) {
    val uiState by captureViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isCaptureSaved) {
        if (uiState.isCaptureSaved) {
            onCaptureCompleted()
        }
    }
}
