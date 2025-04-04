import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.PlaceholderImageRes
import com.fguyet.captioned.presentation.screen.feed.FeedUiItem
import com.fguyet.captioned.presentation.screen.feed.FeedUiState
import com.fguyet.captioned.presentation.screen.feed.canViewCaptures
import com.fguyet.captioned.presentation.screen.feed.uicomponents.CaptureItem
import com.valentinilk.shimmer.shimmer

@Composable
fun FeedItems(
    uiState: FeedUiState,
    bottomInnerPadding: Dp,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        val items = buildList {
            uiState.userCaptureUiItem?.let {
                add(FeedUiItem.CategoryTitle("\uD83D\uDDBC\uFE0F Your take"))
                add(it)
            }
            add(FeedUiItem.CategoryTitle("\uD83D\uDC40 Look at your friends' takes"))
            uiState.friendsCaptureUiItems?.let { addAll(it) } ?: run {
                repeat(3) {
                    add(FeedUiItem.CapturePlaceHolder)
                }
            }
        }

        itemsIndexed(items) { index, item ->
            // TODO store info in view model and repository
            when (item) {
                is FeedUiItem.CaptureUiItem -> {
                    var liked by remember { mutableStateOf(false) }
                    var isLoading by remember { mutableStateOf(true) }
                    CaptureItemContainer(isLoading = isLoading) {
                        CaptureItem(
                            // TODO fetch image remotely when imageRes is Remote
                            imageResId = item.imageRes.drawableResId,
                            userName = item.userName,
                            isHidden = !uiState.canViewCaptures,
                            isLiked = liked,
                            onImageLoaded = { isLoading = false },
                            onLikeChange = { liked = it },
                        )
                    }
                }

                is FeedUiItem.CategoryTitle -> {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                FeedUiItem.CapturePlaceHolder -> {
                    CaptureItemContainer()
                }
            }

            if (index == items.lastIndex) {
                Spacer(modifier = modifier.height(bottomInnerPadding))
            }
        }
    }
}

@Composable
private fun CaptureItemContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    content: @Composable () -> Unit = { },
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .aspectRatio(1f, true)
            .clip(shape = RoundedCornerShape(8.dp))
            .let {
                if (isLoading) it
                    .background(Color.LightGray)
                    .shimmer()
                else it
            },
        content = content
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
