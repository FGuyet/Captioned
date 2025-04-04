package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.CapturesRepository

class GetFriendCapturesUseCase(
    private val getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val getFriendUserIdsUseCase: GetFriendUserIdsUseCase,
    private val capturesRepository: CapturesRepository,
    private val getCurrentCaptionIdUseCase: GetCurrentCaptionUseCase
) {
    suspend operator fun invoke() = capturesRepository.getCaptures(
        userIds = getFriendUserIdsUseCase(getActiveUserIdUseCase()),
        captionId = getCurrentCaptionIdUseCase().id
    )
}