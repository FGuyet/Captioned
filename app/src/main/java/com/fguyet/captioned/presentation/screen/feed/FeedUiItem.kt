package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.User


sealed class FeedUiItem {
    data class CategoryTitle(
        val title: String,
    ) : FeedUiItem()

    data class CaptureUiItem(
        val id: String,
        val userName: String,
        val imageRes: ImageRes,
        val canBeLiked: Boolean = true,
        val likesCount: Int? = null,
    ) : FeedUiItem()

    data object CapturePlaceHolder : FeedUiItem()

    data class RemindFriendItem(val user: User) : FeedUiItem()

    data object InviteFriendAction : FeedUiItem()
}
