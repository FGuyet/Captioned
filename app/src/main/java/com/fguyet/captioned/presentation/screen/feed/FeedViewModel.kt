package com.fguyet.captioned.presentation.screen.feed

import com.fguyet.captioned.core.commons.CaptionedViewModel
import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.entity.User
import com.fguyet.captioned.domain.usecase.GetActiveUserCaptureUseCase
import com.fguyet.captioned.domain.usecase.GetCurrentCaptionUseCase
import com.fguyet.captioned.domain.usecase.GetFriendCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetFriendsUseCase
import com.fguyet.captioned.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlin.time.Duration.Companion.seconds

internal class FeedViewModel(
    private val getUserCaptureUseCase: GetActiveUserCaptureUseCase,
    private val getFriendCapturesUseCase: GetFriendCapturesUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getCurrentCaptionUseCase: GetCurrentCaptionUseCase
) : CaptionedViewModel<FeedUiState>(initialUiState = FeedUiState(isLoading = true)) {

    init {
        launch {
            // simulating live reactions to active user's capture
            uiState.map { it.userCaptureUiItem != null }.distinctUntilChanged().collectLatest { activeUserHasParticipated ->
                if (activeUserHasParticipated) {
                    while (currentCoroutineContext().isActive) {
                        delay(2.seconds)
                        updateUiState { copy(userCaptureUiItem = userCaptureUiItem?.copy(likesCount = (userCaptureUiItem.likesCount ?: 0) + 1)) }
                    }
                }

            }
        }

    }

    fun refreshData() {
        launch {
            updateUiState { copy(isLoading = true) }
            coroutineScope {
                launch {
                    val caption = getCurrentCaptionUseCase()
                    updateUiState { copy(caption = caption) }
                }
                launch {
                    val userCaptureUiItem = getUserCaptureUseCase()?.toCaptureUiItem(isFromActiveUser = true)
                    updateUiState { copy(userCaptureUiItem = userCaptureUiItem) }
                }
                launch {
                    val friendCaptures = getFriendCapturesUseCase()
                    val friendsCaptureUiItems = friendCaptures.map { it.toCaptureUiItem(isFromActiveUser = false) }
                    val friends = getFriendsUseCase()
                    updateUiState {
                        copy(
                            friendsCaptureUiItems = friendsCaptureUiItems,
                            pendingFriendCaptures = friends.filterNot { friend ->
                                friend.id in friendCaptures.map { it.userId }
                            }
                        )
                    }
                }
            }
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun Capture.toCaptureUiItem(isFromActiveUser: Boolean): FeedUiItem.CaptureUiItem = FeedUiItem.CaptureUiItem(
        id = id.id,
        userName = getUserNameUseCase(userId) ?: "Unknown",
        imageRes = imageRes,
        canBeLiked = !isFromActiveUser,
        likesCount = if (isFromActiveUser) 0 else null
    )

    fun remindFriend(user: User) {
        launch {
            updateUiState { copy(pendingFriendCaptures = pendingFriendCaptures - user) }
        }
    }
}