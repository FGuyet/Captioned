package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.repository.CaptionsRepository
import com.fguyet.captioned.domain.repository.getCaption
import java.time.ZonedDateTime

class GetCurrentCaptionUseCase(private val captionsRepository: CaptionsRepository) {
    operator fun invoke(): Caption = captionsRepository.getCaption(ZonedDateTime.now())
}
