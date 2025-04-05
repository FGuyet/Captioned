package com.fguyet.captioned.presentation.screen.feed

import CaptionHeader
import FeedItems
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.core.designsystem.CaptionedButtonConfig
import com.fguyet.captioned.core.designsystem.CaptionedScreen
import com.fguyet.captioned.domain.entity.Caption
import com.fguyet.captioned.domain.entity.CaptionId
import com.fguyet.captioned.domain.entity.CaptionValidity
import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.PlaceholderImageRes
import com.fguyet.captioned.domain.entity.User
import com.fguyet.captioned.presentation.theme.CaptionedTheme
import java.time.ZonedDateTime

@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onCapture: () -> Unit = {},
    onRemindFriend: (user: User) -> Unit = {},
) {
    CaptionedScreen(
        modifier = modifier,
        isLoading = uiState.isLoading,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    )
            ) {
                CaptionHeader(uiState.caption)

                FeedItems(
                    uiState = uiState,
                    bottomInnerPadding = paddingValues.calculateBottomPadding() + if (!uiState.canViewCaptures) 48.dp else 0.dp,
                    onRemindFriend = onRemindFriend,
                )
            }
        },
        actionButtonConfig = CaptionedButtonConfig(
            text = stringResource(R.string.share_your_take),
            onClick = onCapture,
        ).takeUnless { uiState.isLoading || uiState.canViewCaptures },
    )
}

@Preview
@Composable
internal fun FeedScreenPreview() {
    CaptionedTheme {
        FeedScreen(
            uiState = FeedUiState(
                caption = Caption(
                    id = CaptionId("caption_1"),
                    text = "This is a caption",
                    validity = CaptionValidity(
                        start = ZonedDateTime.now().minusHours(2),
                        end = ZonedDateTime.now().plusSeconds(20)
                    )
                ),
                friendsCaptureUiItems = listOf(
                    FeedUiItem.CaptureUiItem(
                        id = "1",
                        userName = "User Name",
                        imageRes = ImageRes.Placeholder(PlaceholderImageRes.OrganizedChaos(id = 2)),
                    ),
                    FeedUiItem.CaptureUiItem(
                        id = "2",
                        userName = "User Name",
                        imageRes = ImageRes.Placeholder(PlaceholderImageRes.OrganizedChaos(id = 3)),
                    ),
                ),
            ),
        )
    }
}
