package com.fguyet.captioned.domain.entity

import com.fguyet.captioned.domain.repository.UserId

data class Capture(
    val id: CaptureId,
    val userId: UserId,
    val imageUri: String,
    val captionId: CaptionId,
)
