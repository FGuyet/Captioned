package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.repository.FriendsRepository
import com.fguyet.captioned.domain.repository.UserId

internal class FakeFriendsRepository : FriendsRepository {
    override fun getFriends(userId: UserId): List<UserId> = fakeFriendsId

    companion object {
        internal val fakeFriendsId = (1..10).map { UserId("friend$it") }
    }
}