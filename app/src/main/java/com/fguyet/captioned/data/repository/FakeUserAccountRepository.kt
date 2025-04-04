package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.repository.UserAccountRepository
import com.fguyet.captioned.domain.repository.UserId
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class FakeUserAccountRepository : UserAccountRepository {
    override var userId: UserId? = null

    override suspend fun setUserId(userId: String) {
        // Simulate a delay to mimic network or database operation
        delay(1.seconds)
        this.userId = UserId(userId)
    }
}


