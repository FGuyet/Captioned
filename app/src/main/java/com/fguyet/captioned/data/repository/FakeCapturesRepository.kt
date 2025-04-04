package com.fguyet.captioned.data.repository

import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.Capture
import com.fguyet.captioned.domain.entity.CaptureId
import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.PlaceholderImageRes
import com.fguyet.captioned.domain.repository.CapturesRepository
import com.fguyet.captioned.domain.repository.UserId
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

private val FAKE_NETWORK_DELAY = 1.seconds

internal class FakeCapturesRepository : CapturesRepository {
    private val communityUserIds by lazy { (1..10).map { UserId(id = "user_$it") } }
    private var userCapture: Capture? = null

    override suspend fun getCaptures(captionId: CaptionId, userIds: List<UserId>?): List<Capture> {
        // Simulate a delay to mimic network or database operation
        delay(FAKE_NETWORK_DELAY)

        val userIdList = userIds ?: communityUserIds

        return userIdList.mapIndexed { index, userId ->
            if (userId == FakeAccountRepository.fakeUserId) return@mapIndexed userCapture

            val captureId = CaptureId(id = "${captionId.id}_${userId.id}")
            Capture(
                id = captureId,
                userId = userId,
                imageRes = ImageRes.Placeholder(PlaceholderImageRes.OrganizedChaos(id = index + 2)),
                captionId = captionId,
                dateTime = LocalDateTime.now().minusMinutes((0..120L).random())
            )
        }.filterNotNull()
    }

    override suspend fun saveCapture(capture: Capture) {
        // Simulate saving the capture
        delay(FAKE_NETWORK_DELAY)
        userCapture = capture
    }
}


