package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.CapturesRepository

class GetActiveUserCaptureUseCase(
    private val getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val capturesRepository: CapturesRepository,
    private val getCurrentCaptionIdUseCase: GetCurrentCaptionUseCase
) {
    suspend operator fun invoke() = capturesRepository.getCaptures(
        userIds = listOf(getActiveUserIdUseCase()),
        captionId = getCurrentCaptionIdUseCase().id
    ).first()
}
