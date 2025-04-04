package com.fguyet.captioned.presentation.screen.capture

import com.fguyet.captioned.core.commons.CaptionedViewModel
import com.fguyet.captioned.domain.usecase.GetCurrentCaptionUseCase
import com.fguyet.captioned.domain.usecase.SaveCaptureUseCase

internal class CaptureViewModel(
    private val saveCaptureUseCase: SaveCaptureUseCase,
    private val getCurrentCaptionUseCase: GetCurrentCaptionUseCase
) : CaptionedViewModel<CaptureUiState>(initialUiState = CaptureUiState()) {
    init {
        launch {
            updateUiState { copy(isLoading = true) }
            saveCaptureUseCase("", captionId = getCurrentCaptionUseCase().id)
            updateUiState { copy(isCaptureSaved = true) }
        }
    }
}