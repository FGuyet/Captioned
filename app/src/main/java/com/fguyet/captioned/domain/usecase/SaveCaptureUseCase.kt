package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.repository.AccountRepository
import com.fguyet.captioned.domain.repository.CapturesRepository

class SaveCaptureUseCase(
    private val capturesRepository: CapturesRepository,
    private val accountRepository: AccountRepository,
) {
    // TODO pass data to save
    suspend operator fun invoke(imageData: String, captionId: CaptionId) {
        accountRepository.userId?.let {
            capturesRepository.createCapture(
                imageData = imageData,
                userId = it,
                captionId = captionId
            )
        }
    }
}
