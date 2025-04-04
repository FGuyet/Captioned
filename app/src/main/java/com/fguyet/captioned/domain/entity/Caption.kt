package com.fguyet.captioned.domain.entity

data class Caption(
    val id: CaptionId,
    val text: String,
    val validity: CaptionValidity
)