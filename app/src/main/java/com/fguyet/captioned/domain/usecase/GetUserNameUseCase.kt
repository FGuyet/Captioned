package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserId
import com.fguyet.captioned.domain.repository.UsersRepository

class GetUserNameUseCase(private val repository: UsersRepository) {
    operator fun invoke(userId: UserId): String = repository.getUserName(userId) ?: userId.toString()
}
