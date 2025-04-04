package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.repository.UserId
import com.fguyet.captioned.domain.repository.UsersRepository

class FakeUsersRepository : UsersRepository {
    private val fakeFriends = listOf(
        UserId("friend1") to "Marc Zuckerberg",
        UserId("friend2") to "Elon Musk",
        UserId("friend3") to "Bill Gates",
    ).toMap()

    override fun getFriendIds(userId: UserId): List<UserId> = fakeFriends.keys.toList()

    override fun getUserName(userId: UserId): String? = fakeFriends[userId]
}