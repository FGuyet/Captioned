package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserAccountRepository

class GetActiveUserIdUseCase(private val userAccountRepository: UserAccountRepository) {
    operator fun invoke() = userAccountRepository.userId
}
