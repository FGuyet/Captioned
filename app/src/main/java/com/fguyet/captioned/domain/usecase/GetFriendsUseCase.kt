package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.entity.User
import com.fguyet.captioned.domain.repository.UsersRepository

internal class GetFriendsUseCase(
    private val getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val getFriendUserIdsUseCase: GetFriendUserIdsUseCase,
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): List<User> {
        val userId = getActiveUserIdUseCase() ?: return emptyList()
        return getFriendUserIdsUseCase(userId).mapNotNull { usersRepository.getUser(it) }
    }
}