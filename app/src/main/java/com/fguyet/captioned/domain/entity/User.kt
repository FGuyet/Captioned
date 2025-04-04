package com.fguyet.captioned.domain.entity

import com.fguyet.captioned.domain.repository.UserId

data class User(
    val id: UserId,
    val name: String,
)