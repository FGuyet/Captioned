package com.fguyet.captioned.presentation.screen.welcome

import com.fguyet.captioned.core.commons.CaptionedViewModel
import com.fguyet.captioned.domain.usecase.LoginUseCase

internal class WelcomeViewModel(private val loginUseCase: LoginUseCase) : CaptionedViewModel<WelcomeUiState>(initialUiState = WelcomeUiState()) {
    fun login() {
        launch {
            updateUiState { copy(isLoading = true) }
            loginUseCase()
            updateUiState { copy(isLoggedIn = true) }
        }
    }
}