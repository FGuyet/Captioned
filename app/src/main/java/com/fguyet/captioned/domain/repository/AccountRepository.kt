package com.fguyet.captioned.domain.repository

interface AccountRepository {
    val userId: UserId?
    suspend fun createUser()
}
