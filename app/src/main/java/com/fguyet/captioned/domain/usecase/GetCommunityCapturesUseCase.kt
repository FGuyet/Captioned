package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.CapturesRepository

internal class GetCommunityCapturesUseCase(
    private val capturesRepository: CapturesRepository,
    private val getCurrentCaptionIdUseCase: GetCurrentCaptionUseCase
) {
    suspend operator fun invoke() = capturesRepository.getCaptures(
        userIds = null,
        captionId = getCurrentCaptionIdUseCase().id
    )
}