package com.fguyet.captioned.domain.repository

import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import kotlinx.coroutines.flow.StateFlow
import java.time.ZonedDateTime

interface CaptionsRepository {
    val captions: StateFlow<List<Caption>>
}

fun CaptionsRepository.getCaption(dateTime: ZonedDateTime): Caption =
    captions.value.firstOrNull { caption ->
        dateTime.isAfter(caption.validity.start) && dateTime.isBefore(caption.validity.end)
    } ?: throw IllegalStateException("No caption found for the given date time")

fun CaptionsRepository.getCaption(id: CaptionId): Caption =
    captions.value.firstOrNull { caption -> caption.id == id } ?: throw IllegalStateException("No caption found for the given id")
