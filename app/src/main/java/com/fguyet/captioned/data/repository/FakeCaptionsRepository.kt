package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.CaptionValidity
import com.fguyet.captioned.domain.repository.CaptionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds


private val initialOffset = 20.seconds
private val validityDuration = 1.days

internal class FakeCaptionsRepository : CaptionsRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val initialDateTime = ZonedDateTime.now()

    init {
        // Simulate being notified of a new caption
        coroutineScope.launch {
            delay(initialOffset)
            _captions.value += newFakeCaption
        }
    }

    private val newFakeCaptionStart = initialDateTime.plusSeconds(initialOffset.inWholeSeconds)
    private val newFakeCaption = Caption(
        id = CaptionId("caption_2"),
        text = "The calm before the storm",
        validity = CaptionValidity(
            start = newFakeCaptionStart,
            end = newFakeCaptionStart.plusDays(validityDuration.inWholeDays)
        )
    )

    private val previousFakeCaption = Caption(
        id = CaptionId("caption_1"),
        text = "Organized chaos",
        validity = CaptionValidity(
            start = newFakeCaptionStart.minusDays(validityDuration.inWholeDays),
            end = newFakeCaptionStart,
        )
    )

    private val _captions = MutableStateFlow(listOf(previousFakeCaption))
    override val captions = _captions.asStateFlow()
}