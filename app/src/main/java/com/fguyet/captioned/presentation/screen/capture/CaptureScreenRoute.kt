package com.fguyet.captioned.presentation.screen.capture

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CaptureScreenRoute(
    modifier: Modifier = Modifier,
    onCaptureCompleted: () -> Unit,
    onCaptureCancelled: () -> Unit,
    captureViewModel: CaptureViewModel = koinViewModel()
) {
    val uiState by captureViewModel.uiState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(uiState.isCaptureSaved) {
        if (uiState.isCaptureSaved) {
            Toast.makeText(
                context,
                "Capture shared! (mocked action)",
                Toast.LENGTH_SHORT
            ).show()
            onCaptureCompleted()
        }
    }
}
