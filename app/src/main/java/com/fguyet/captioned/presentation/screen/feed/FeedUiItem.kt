package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.domain.entity.ImageRes


sealed class FeedUiItem {
    data class CategoryTitle(
        val title: String,
    ) : FeedUiItem()

    data class CaptureUiItem(
        val id: String,
        val userName: String,
        val imageRes: ImageRes,
    ) : FeedUiItem()

    data object CapturePlaceHolder : FeedUiItem()
}
