package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.core.commons.CaptionedViewModel
import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.usecase.GetActiveUserCaptureUseCase
import com.fguyet.captioned.domain.usecase.GetCurrentCaptionUseCase
import com.fguyet.captioned.domain.usecase.GetFriendCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetFriendsUseCase
import com.fguyet.captioned.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.coroutineScope

internal class FeedViewModel(
    private val getUserCaptureUseCase: GetActiveUserCaptureUseCase,
    private val getFriendCapturesUseCase: GetFriendCapturesUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getCurrentCaptionUseCase: GetCurrentCaptionUseCase
) : CaptionedViewModel<FeedUiState>(initialUiState = FeedUiState(isLoading = true)) {

    fun refreshData() {
        launch {
            updateUiState { copy(isLoading = true) }
            coroutineScope {
                launch {
                    val caption = getCurrentCaptionUseCase()
                    updateUiState { copy(caption = caption) }
                }
                launch {
                    val userCaptureUiItem = getUserCaptureUseCase()?.toCaptureUiItem()
                    updateUiState { copy(userCaptureUiItem = userCaptureUiItem) }
                }
                launch {
                    val friendsCaptureUiItems = getFriendCapturesUseCase().map { it.toCaptureUiItem() }
                    updateUiState { copy(friendsCaptureUiItems = friendsCaptureUiItems) }
                }
            }
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun Capture.toCaptureUiItem(): FeedUiItem.CaptureUiItem = FeedUiItem.CaptureUiItem(
        id = id.id,
        userName = getUserNameUseCase(userId) ?: "Unknown",
        imageRes = imageRes,
    )
}