package com.fguyet.captioned.presentation.screen.feed

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.core.designsystem.CaptionedScreen
import com.fguyet.captioned.presentation.screen.feed.capture.CaptureItem
import com.fguyet.captioned.presentation.theme.CaptionedTheme

private val fakeImages = listOf(
    R.drawable.organized_chaos_2,
    R.drawable.organized_chaos_3,
    R.drawable.organized_chaos_4
)

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onCapture: () -> Unit = {},
) {
    CaptionedScreen(modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            itemsIndexed(uiState.friendsCaptureUiItems) { index, item ->
                // TODO store info in view model and repository
                var liked by remember { mutableStateOf(false) }

                CaptureItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f, true)
                        .padding(vertical = 16.dp),
                    // TODO fetch image from network based on imageUri
                    imageResId = fakeImages[index],
                    userName = item.userName,
                    isHidden = !uiState.canViewCaptures,
                    isLiked = liked,
                    onLikeChange = { liked = it },
                )
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    CaptionedTheme {
        FeedScreen(
            uiState = FeedUiState(
                friendsCaptureUiItems = listOf(
                    FeedUiItem.CaptureUiItem(
                        id = "1",
                        userName = "User Name",
                        imageUri = "TODO()",
                        caption = "TODO()",
                    ),
                    FeedUiItem.CaptureUiItem(
                        id = "2",
                        userName = "User Name",
                        imageUri = "TODO()",
                        caption = "TODO()",
                    ),
                ),
            ),
        )
    }
}

