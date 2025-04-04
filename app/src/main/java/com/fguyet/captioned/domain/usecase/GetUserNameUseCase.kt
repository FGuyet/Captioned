package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserId
import com.fguyet.captioned.domain.repository.UsersRepository

internal class GetUserNameUseCase(private val repository: UsersRepository) {
    operator fun invoke(userId: UserId): String? = repository.getUser(userId)?.name
}
