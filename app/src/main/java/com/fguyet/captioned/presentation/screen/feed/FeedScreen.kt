package com.fguyet.captioned.presentation.screen.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.core.designsystem.CaptionedButtonConfig
import com.fguyet.captioned.core.designsystem.CaptionedScreen
import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.PlaceholderImageRes
import com.fguyet.captioned.presentation.screen.feed.capture.CaptureItem
import com.fguyet.captioned.presentation.theme.CaptionedTheme


@Composable
internal fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onCapture: () -> Unit = {},
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
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Today's caption is: ${uiState.caption}",
                    style = MaterialTheme.typography.titleLarge,
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    val items = buildList {
                        uiState.userCaptureUiItem?.let {
                            add(FeedUiItem.CategoryTitle("Your take"))
                            add(it)
                        }
                        add(FeedUiItem.CategoryTitle("Your friends' takes"))
                        addAll(uiState.friendsCaptureUiItems)
                    }

                    itemsIndexed(items) { index, item ->
                        // TODO store info in view model and repository
                        when (item) {
                            is FeedUiItem.CaptureUiItem -> {
                                var liked by remember { mutableStateOf(false) }

                                CaptureItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .aspectRatio(1f, true),
                                    // TODO fetch image remotely when imageRes is Remote
                                    imageResId = item.imageRes.drawableResId,
                                    userName = item.userName,
                                    isHidden = !uiState.canViewCaptures,
                                    isLiked = liked,
                                    onLikeChange = { liked = it },
                                )
                            }

                            is FeedUiItem.CategoryTitle -> {
                                Text(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    text = item.title,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }

                        if (index == items.lastIndex) {
                            Spacer(modifier = modifier.height(paddingValues.calculateBottomPadding() + 16.dp))
                        }
                    }
                }
            }
        },
        actionButtonConfig = CaptionedButtonConfig(
            text = "Capture my take",
            onClick = onCapture,
            enabled = !uiState.isLoading,
        ),
    )
}

private val ImageRes.drawableResId
    get() = when (this) {
        is ImageRes.Placeholder -> placeholderImageRes.drawableResId
        is ImageRes.Remote -> R.drawable.app_logo
    }

private val PlaceholderImageRes.drawableResId
    get() = when (this) {
        is PlaceholderImageRes.OrganizedChaos -> when (id % 4) {
            0 -> R.drawable.organized_chaos_4
            1 -> R.drawable.organized_chaos_1
            2 -> R.drawable.organized_chaos_2
            3 -> R.drawable.organized_chaos_3
            else -> R.drawable.app_logo
        }
    }

@Preview
@Composable
internal fun FeedScreenPreview() {
    CaptionedTheme {
        FeedScreen(
            uiState = FeedUiState(
                friendsCaptureUiItems = listOf(
                    FeedUiItem.CaptureUiItem(
                        id = "1",
                        userName = "User Name",
                        imageRes = ImageRes.Placeholder(PlaceholderImageRes.OrganizedChaos(id = 2)),
                        caption = "TODO()",
                    ),
                    FeedUiItem.CaptureUiItem(
                        id = "2",
                        userName = "User Name",
                        imageRes = ImageRes.Placeholder(PlaceholderImageRes.OrganizedChaos(id = 3)),
                        caption = "TODO()",
                    ),
                ),
            ),
        )
    }
}

