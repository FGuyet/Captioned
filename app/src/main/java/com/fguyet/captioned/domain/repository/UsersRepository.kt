package com.fguyet.captioned.domain.repository

import com.fguyet.captioned.domain.entity.User

interface UsersRepository {
    fun getUser(userId: UserId): User?
}

