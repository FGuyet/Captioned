package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.entity.CaptureId
import com.fguyet.captioned.domain.repository.CapturesRepository
import com.fguyet.captioned.domain.repository.UserId
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

private val FAKE_NETWORK_DELAY = 1.seconds

class FakeCapturesRepository : CapturesRepository {

    private val communityUserIds by lazy { (1..10).map { UserId(id = "user_$it") } }

    override suspend fun getCaptures(captionId: CaptionId, userIds: List<UserId>?): List<Capture> {
        // Simulate a delay to mimic network or database operation
        delay(FAKE_NETWORK_DELAY)

        val userIdList = userIds ?: communityUserIds

        return userIdList.map { userId ->
            val captureId = CaptureId(id = "${captionId.id}_${userId.id}")
            Capture(
                id = captureId,
                userId = userId,
                imageUri = "https://example.com/$captureId.jpg",
                captionId = captionId
            )
        }
    }
}


