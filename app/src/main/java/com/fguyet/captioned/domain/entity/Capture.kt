package com.fguyet.captioned.domain.entity

import com.fguyet.captioned.domain.repository.UserId
import java.time.LocalDateTime

data class Capture(
    val id: CaptureId,
    val userId: UserId,
    val imageRes: ImageRes,
    val captionId: CaptionId,
    val dateTime: LocalDateTime,
)


