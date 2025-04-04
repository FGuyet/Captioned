package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.repository.CaptionsRepository
import com.fguyet.captioned.domain.repository.getCaption

class GetCaptionUseCase(private val repository: CaptionsRepository) {
    operator fun invoke(id: CaptionId): Caption = repository.getCaption(id)
}