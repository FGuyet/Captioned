package com.fguyet.captioned

import com.fguyet.captioned.data.repository.FakeAccountRepository
import com.fguyet.captioned.data.repository.FakeCaptionsRepository
import com.fguyet.captioned.data.repository.FakeCapturesRepository
import com.fguyet.captioned.data.repository.FakeFriendsRepository
import com.fguyet.captioned.data.repository.FakeUsersRepository
import com.fguyet.captioned.domain.repository.AccountRepository
import com.fguyet.captioned.domain.repository.CaptionsRepository
import com.fguyet.captioned.domain.repository.CapturesRepository
import com.fguyet.captioned.domain.repository.FriendsRepository
import com.fguyet.captioned.domain.repository.UsersRepository
import com.fguyet.captioned.domain.usecase.GetActiveUserCaptureUseCase
import com.fguyet.captioned.domain.usecase.GetActiveUserIdUseCase
import com.fguyet.captioned.domain.usecase.GetCaptionUseCase
import com.fguyet.captioned.domain.usecase.GetCommunityCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetCurrentCaptionUseCase
import com.fguyet.captioned.domain.usecase.GetFriendCapturesUseCase
import com.fguyet.captioned.domain.usecase.GetFriendIdsUseCase
import com.fguyet.captioned.domain.usecase.GetFriendUserIdsUseCase
import com.fguyet.captioned.domain.usecase.GetUserNameUseCase
import com.fguyet.captioned.domain.usecase.LoginUseCase
import com.fguyet.captioned.domain.usecase.SaveCaptureUseCase
import com.fguyet.captioned.presentation.screen.capture.CaptureViewModel
import com.fguyet.captioned.presentation.screen.feed.FeedViewModel
import com.fguyet.captioned.presentation.screen.welcome.WelcomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val presentationCaptionedKoinModule = module {
    viewModelOf(::FeedViewModel)
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::CaptureViewModel)
}

val domainCaptionedKoinModule = module {
    factoryOf(::GetActiveUserCaptureUseCase)
    factoryOf(::GetActiveUserIdUseCase)
    factoryOf(::GetCaptionUseCase)
    factoryOf(::GetCommunityCapturesUseCase)
    factoryOf(::GetCurrentCaptionUseCase)
    factoryOf(::GetFriendCapturesUseCase)
    factoryOf(::GetFriendIdsUseCase)
    factoryOf(::GetFriendUserIdsUseCase)
    factoryOf(::GetUserNameUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::SaveCaptureUseCase)
}

val dataCaptionedKoinModule = module {
    single<CaptionsRepository> { FakeCaptionsRepository() }
    single<CapturesRepository> { FakeCapturesRepository() }
    single<AccountRepository> { FakeAccountRepository() }
    single<UsersRepository> { FakeUsersRepository() }
    single<FriendsRepository> { FakeFriendsRepository() }
}

val captionedKoinModule = module {
    includes(
        presentationCaptionedKoinModule,
        domainCaptionedKoinModule,
        dataCaptionedKoinModule
    )
}