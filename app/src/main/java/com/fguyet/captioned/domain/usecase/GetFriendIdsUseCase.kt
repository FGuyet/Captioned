package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.FriendsRepository
import com.fguyet.captioned.domain.repository.UserId

internal class GetFriendIdsUseCase(private val friendsRepository: FriendsRepository) {
    operator fun invoke(userId: UserId) = friendsRepository.getFriends(userId)
}
