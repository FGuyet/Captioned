package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.CaptionValidity
import com.fguyet.captioned.domain.repository.CaptionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours


private val pastHoursOffset = 2.hours
private val validityDuration = 1.days

internal class FakeCaptionsRepository : CaptionsRepository {
    private val initialDateTime = ZonedDateTime.now()

    private val currentFakeCaptionStart = initialDateTime.minusHours(pastHoursOffset.inWholeHours)
    private val currentFakeCaption = Caption(
        id = CaptionId("caption_2"),
        text = "Organized chaos",
        validity = CaptionValidity(
            start = currentFakeCaptionStart,
            end = currentFakeCaptionStart.plusDays(validityDuration.inWholeDays)
        )
    )

    private val previousFakeCaption = Caption(
        id = CaptionId("caption_1"),
        text = "The calm before the storm",
        validity = CaptionValidity(
            start = currentFakeCaptionStart.minusDays(validityDuration.inWholeDays),
            end = currentFakeCaptionStart,
        )
    )

    private val _captions = MutableStateFlow(listOf(previousFakeCaption, currentFakeCaption))
    override val captions = _captions.asStateFlow()
}