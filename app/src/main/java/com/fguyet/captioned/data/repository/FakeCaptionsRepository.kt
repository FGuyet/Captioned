package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.CaptionValidity
import com.fguyet.captioned.domain.repository.CaptionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds


private val initialOffset = 20.seconds
private val validityDuration = 1.days

class FakeCaptionsRepository : CaptionsRepository {
    private val initialDateTime = ZonedDateTime.now()


    private val newFakeCaptionStart = initialDateTime.plusSeconds(initialOffset.inWholeSeconds)
    private val newFakeCaption = Caption(
        id = CaptionId("caption_2"),
        text = "The calm before the storm",
        validity = CaptionValidity(
            start = newFakeCaptionStart,
            end = newFakeCaptionStart.plusDays(1)
        )
    )

    private val previousFakeCaption = Caption(
        id = CaptionId("caption_1"),
        text = "Organized chaos",
        validity = CaptionValidity(
            start = newFakeCaptionStart.minusDays(2),
            end = newFakeCaptionStart.minusDays(1),
        )
    )

    private val _captions = MutableStateFlow(listOf(previousFakeCaption))
    override val captions = _captions.asStateFlow()
}