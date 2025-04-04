package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.AccountRepository

internal class LoginUseCase(
    private val accountRepository: AccountRepository,
) {
    // TODO pass credentials as arguments
    suspend operator fun invoke() = accountRepository.login()
}
