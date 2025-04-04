package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.domain.entity.Caption

data class FeedUiState(
    val userCaptureUiItem: FeedUiItem.CaptureUiItem? = null,
    val friendsCaptureUiItems: List<FeedUiItem.CaptureUiItem> = emptyList(),
    val communityCaptureUiItems: List<FeedUiItem.CaptureUiItem> = emptyList(),
    val caption: Caption? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)


val FeedUiState.canViewCaptures: Boolean
    get() = userCaptureUiItem != null