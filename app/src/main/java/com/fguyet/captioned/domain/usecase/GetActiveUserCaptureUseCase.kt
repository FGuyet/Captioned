package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.repository.CapturesRepository

class GetActiveUserCaptureUseCase(
    private val getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val capturesRepository: CapturesRepository,
    private val getCurrentCaptionIdUseCase: GetCurrentCaptionUseCase
) {
    suspend operator fun invoke(): Capture? {
        val activeUserId = getActiveUserIdUseCase() ?: return null
        return capturesRepository.getCaptures(
            userIds = listOf(activeUserId),
            captionId = getCurrentCaptionIdUseCase().id
        ).first()
    }
}
