package com.fguyet.captioned.presentation.screen.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
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
import com.fguyet.captioned.presentation.screen.feed.capture.CaptureItem
import com.fguyet.captioned.presentation.theme.CaptionedTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration
import java.time.Duration as JavaDuration

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "\uD83D\uDCAC Today's caption",
                            style = MaterialTheme.typography.titleLarge,
                        )

                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = uiState.caption?.text?.let { "“$it”" } ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    uiState.caption?.let { caption ->
                        var remainingTime by remember {
                            mutableStateOf<Duration?>(null)
                        }

                        LaunchedEffect(caption.validity.end) {
                            while (isActive) {
                                delay(1.seconds)
                                remainingTime = JavaDuration.between(
                                    ZonedDateTime.now(),
                                    caption.validity.end,
                                ).toKotlinDuration().takeUnless { it < Duration.ZERO }
                            }
                        }

                        val remainingTimeStr by remember(caption.validity.end) {
                            derivedStateOf {
                                remainingTime?.formatToRemainingTime()
                            }
                        }

                        remainingTimeStr?.let {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "⏱️ $it",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    val items = buildList {
                        uiState.userCaptureUiItem?.let {
                            add(FeedUiItem.CategoryTitle("Your take"))
                            add(it)
                        }
                        add(FeedUiItem.CategoryTitle("\uD83D\uDC40 Look at your friends' takes"))
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
            text = "\uD83D\uDCF8  Share your take",
            onClick = onCapture,
            enabled = !uiState.isLoading,
        ).takeUnless { uiState.canViewCaptures },
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

private fun Duration.formatToRemainingTime(): String {
    val totalSeconds = inWholeSeconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        hours >= 10 -> "${hours}h"
        hours >= 1 -> "${hours}h ${minutes}m"
        minutes >= 10 -> "${minutes}m"
        else -> "${minutes}m ${seconds}s"
    }
}