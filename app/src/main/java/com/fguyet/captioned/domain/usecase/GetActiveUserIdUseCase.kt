package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.AccountRepository

internal class GetActiveUserIdUseCase(private val userAccountRepository: AccountRepository) {
    operator fun invoke() = userAccountRepository.userId
}
