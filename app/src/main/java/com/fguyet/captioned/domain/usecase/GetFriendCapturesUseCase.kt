package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.repository.CapturesRepository

internal class GetFriendCapturesUseCase(
    private val getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val getFriendUserIdsUseCase: GetFriendUserIdsUseCase,
    private val capturesRepository: CapturesRepository,
    private val getCurrentCaptionIdUseCase: GetCurrentCaptionUseCase
) {
    suspend operator fun invoke(): List<Capture> {
        val userId = getActiveUserIdUseCase() ?: return emptyList()
        return capturesRepository.getCaptures(
            userIds = getFriendUserIdsUseCase(userId),
            captionId = getCurrentCaptionIdUseCase().id
        )
    }
}