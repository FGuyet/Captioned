package com.fguyet.captioned.domain.repository

interface UserAccountRepository {
    val userId: UserId?
    suspend fun setUserId(userId: String)
}
