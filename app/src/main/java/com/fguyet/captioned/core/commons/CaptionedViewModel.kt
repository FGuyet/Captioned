package com.fguyet.captioned.core.commons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class CaptionedViewModel<T>(initialUiState: T) : ViewModel() {
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    private val uiStateMutex = Mutex()

    protected suspend fun updateUiState(transform: suspend T.() -> T) = uiStateMutex.withLock {
        val oldValue = _uiState.value
        _uiState.value = transform(oldValue).also {
            Log.i("CaptionedViewModel", "uiState updated: $it")
        }
    }

    protected fun launch(block: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Default) { block() }
}