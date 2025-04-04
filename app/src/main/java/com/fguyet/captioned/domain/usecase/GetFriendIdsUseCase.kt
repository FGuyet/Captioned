package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserId
import com.fguyet.captioned.domain.repository.UsersRepository

class GetFriendIdsUseCase(private val usersRepository: UsersRepository) {
    operator fun invoke(userId: UserId) = usersRepository.getFriendIds(userId)
}
