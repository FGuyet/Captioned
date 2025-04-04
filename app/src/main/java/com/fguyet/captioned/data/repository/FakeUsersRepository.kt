package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.User
import com.fguyet.captioned.domain.repository.UserId
import com.fguyet.captioned.domain.repository.UsersRepository

internal class FakeUsersRepository : UsersRepository {
    private val fakeNames = listOf(
        "Alice B.",
        "Bob C.",
        "Charlie D.",
        "David E.",
        "Eve F.",
        "Frank G.",
        "Grace H.",
        "Heidi I.",
        "Ivan J.",
    )

    private val fakeFriends = FakeFriendsRepository.fakeFriendsId.mapIndexed { index, it ->
        User(
            id = it,
            name = fakeNames.getOrNull(index % fakeNames.size) ?: "Anonymous User"
        )
    }

    private val fakeCommunityUsers = listOf(
        User(
            id = UserId("community1"),
            name = "Marc Zuckerberg"
        ),
        User(
            id = UserId("community2"),
            name = "Elon Musk"
        ),
        User(
            id = UserId("community3"),
            name = "Bill Gates"
        ),
    )

    private val activeUser = User(
        id = FakeAccountRepository.fakeUserId,
        name = "Florian G."
    )

    private val fakeUsers = fakeFriends + fakeCommunityUsers + activeUser

    override fun getUser(userId: UserId): User? = fakeUsers.firstOrNull { it.id == userId }
}

