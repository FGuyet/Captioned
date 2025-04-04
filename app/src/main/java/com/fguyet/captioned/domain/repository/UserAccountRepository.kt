package com.fguyet.captioned.domain.repository

interface UserAccountRepository {
    val userId: UserId
    fun setUserId(userId: String)
}
