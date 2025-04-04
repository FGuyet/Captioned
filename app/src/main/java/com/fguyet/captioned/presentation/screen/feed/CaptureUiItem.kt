package com.fguyet.captioned.presentation.screen.feed


sealed class FeedUiItem {
    data class CategoryTitle(
        val title: String,
    ) : FeedUiItem()

    data class CaptureUiItem(
        val id: String,
        val userName: String,
        val imageUri: String,
        val caption: String,
    ) : FeedUiItem()
}
