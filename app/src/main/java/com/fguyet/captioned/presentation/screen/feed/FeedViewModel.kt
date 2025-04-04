package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.core.commons.CaptionedViewModel
import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.usecase.GetActiveUserCaptureUseCase
import com.fguyet.captioned.domain.usecase.GetCaptionUseCase
import com.fguyet.captioned.domain.usecase.GetCommunityCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetFriendCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.coroutineScope

class FeedViewModel(
    private val getUserCaptureUseCase: GetActiveUserCaptureUseCase,
    private val getFriendCapturesUseCase: GetFriendCapturesUseCase,
    private val getCommunityCapturesUseCase: GetCommunityCapturesUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getCaptionUseCase: GetCaptionUseCase,
) : CaptionedViewModel<FeedUiState>(initialUiState = FeedUiState(isLoading = true)) {

    fun refreshData() {
        launch {
            updateUiState { copy(isLoading = true) }
            coroutineScope {
                launch {
                    updateUiState { copy(userCaptureUiItem = getUserCaptureUseCase()?.toCaptureUiItem()) }
                }
                launch {
                    updateUiState { copy(friendsCaptureUiItems = getFriendCapturesUseCase().map { it.toCaptureUiItem() }) }
                }
                launch {
                    updateUiState { copy(communityCaptureUiItems = getCommunityCapturesUseCase().map { it.toCaptureUiItem() }) }
                }
            }
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun Capture.toCaptureUiItem(): FeedUiItem.CaptureUiItem = FeedUiItem.CaptureUiItem(
        id = id.id,
        userName = getUserNameUseCase(userId),
        imageUri = imageUri,
        caption = getCaptionUseCase(captionId).text,
    )
}