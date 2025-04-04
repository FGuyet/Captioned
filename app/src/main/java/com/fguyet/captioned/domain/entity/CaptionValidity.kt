package com.fguyet.captioned.domain.entity

import java.time.ZonedDateTime

data class CaptionValidity(
    val start: ZonedDateTime,
    val end: ZonedDateTime
)