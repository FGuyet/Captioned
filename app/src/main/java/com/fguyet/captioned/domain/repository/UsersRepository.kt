package com.fguyet.captioned.domain.repository

interface UsersRepository {
    fun getFriendIds(userId: UserId): List<UserId>
    fun getUserName(userId: UserId): String?
}
