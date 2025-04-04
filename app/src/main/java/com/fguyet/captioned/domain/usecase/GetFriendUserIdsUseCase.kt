package com.fguyet.captioned.domain.usecase

import com.fguyet.captioned.domain.repository.UserId

internal class GetFriendUserIdsUseCase(
    private val getFriendIdsUseCase: GetFriendIdsUseCase
) {
    operator fun invoke(userId: UserId) = getFriendIdsUseCase(userId)
}
