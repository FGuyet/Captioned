package com.fguyet.captioned.domain.repository

interface FriendsRepository {
    fun getFriends(userId: UserId): List<UserId>
}