package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.repository.AccountRepository
import com.fguyet.captioned.domain.repository.UserId
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

internal class FakeAccountRepository : AccountRepository {
    override var userId: UserId? = null

    override suspend fun login(): UserId {
        // Simulate a delay to mimic network or database operation
        delay(1.seconds)
        return fakeUserId.also { userId = it }
    }

    companion object {
        internal val fakeUserId = UserId("active_user")
    }
}


