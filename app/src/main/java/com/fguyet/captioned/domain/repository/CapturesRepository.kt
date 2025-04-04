package com.fguyet.captioned.domain.repository

import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.Capture

interface CapturesRepository {
    /**
     * Get the captures of a list of users for a specific caption.
     * @param captionId The ID of the caption.
     * @param userIds The list of user IDs. If null, all users will be considered.
     * @return A list of captures for the specified users and caption.
     */
    suspend fun getCaptures(captionId: CaptionId, userIds: List<UserId>? = null): List<Capture>
    suspend fun saveCapture(capture: Capture)
}
