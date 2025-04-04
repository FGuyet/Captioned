package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.AccountRepository

internal class LoginUseCase(private val userAccountRepository: AccountRepository) {
    suspend operator fun invoke() = userAccountRepository.createUser()
}
