package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserAccountRepository

class LoginUseCase(private val userAccountRepository: UserAccountRepository) {
    // TODO: replace with real user id
    suspend operator fun invoke() = userAccountRepository.setUserId("test_user_id")
}
