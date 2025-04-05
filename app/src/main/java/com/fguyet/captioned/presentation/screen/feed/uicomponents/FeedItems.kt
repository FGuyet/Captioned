import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.R
import com.fguyet.captioned.domain.entity.ImageRes
import com.fguyet.captioned.domain.entity.PlaceholderImageRes
import com.fguyet.captioned.domain.entity.User
import com.fguyet.captioned.presentation.screen.feed.FeedUiItem
import com.fguyet.captioned.presentation.screen.feed.FeedUiState
import com.fguyet.captioned.presentation.screen.feed.canViewCaptures
import com.fguyet.captioned.presentation.screen.feed.uicomponents.CaptureItem
import com.valentinilk.shimmer.shimmer

@Composable
fun FeedItems(
    uiState: FeedUiState,
    bottomInnerPadding: Dp,
    onRemindFriend: (user: User) -> Unit,
    modifier: Modifier = Modifier
) {
    val items by remember(uiState) {
        derivedStateOf {
            buildList {
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

                if (uiState.canViewCaptures) {
                    uiState.pendingFriendCaptures.takeUnless { it.isEmpty() }?.let { pendingFriendCaptures ->
                        add(FeedUiItem.CategoryTitle("\uD83D\uDD14 Remind friends to share their takes"))
                        pendingFriendCaptures.take(3).forEach {
                            add(FeedUiItem.RemindFriendItem(it))
                        }
                    }

                    add(FeedUiItem.InviteFriendAction)
                }
            }
        }
    }

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(items, key = { index, it -> it.getKey(index) }) { index, item ->
            // TODO store info in view model and repository
            when (item) {
                is FeedUiItem.CaptureUiItem -> {
                    var liked by rememberSaveable { mutableStateOf(false) }
                    var isLoading by remember { mutableStateOf(true) }
                    CaptureItemContainer(isLoading = isLoading) {
                        CaptureItem(
                            // TODO fetch image remotely when imageRes is Remote
                            imageResId = item.imageRes.drawableResId,
                            userName = item.userName,
                            isHidden = !uiState.canViewCaptures,
                            isLiked = liked,
                            likesCount = item.likesCount,
                            canLike = item.canBeLiked,
                            onImageLoaded = { isLoading = false },
                            onLikeChange = { liked = it },
                        )
                    }
                }

                is FeedUiItem.CategoryTitle -> {
                    Text(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                FeedUiItem.CapturePlaceHolder -> {
                    CaptureItemContainer()
                }

                is FeedUiItem.RemindFriendItem -> {
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = item.user.name,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        ElevatedButton(
                            modifier = Modifier.padding(vertical = 8.dp),
                            onClick = {
                                Toast.makeText(context, "Your request was sent! (mocked action)", Toast.LENGTH_LONG).show()
                                onRemindFriend(item.user)
                            },
                        ) {
                            Text(
                                text = "\uD83D\uDD14",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }

                is FeedUiItem.InviteFriendAction -> {
                    val context = LocalContext.current

                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        onClick = {
                            Toast.makeText(context, "Your invites were sent! (mocked action)", Toast.LENGTH_LONG).show()
                        },
                    ) {
                        Text(
                            text = "✉\uFE0F Invite friends",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
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

private fun FeedUiItem.getKey(index: Int): String = when (this) {
    is FeedUiItem.CaptureUiItem -> "capture_$id"
    is FeedUiItem.CategoryTitle -> title
    is FeedUiItem.RemindFriendItem -> "user_${user.id.id}"
    is FeedUiItem.InviteFriendAction -> "invite"
    is FeedUiItem.CapturePlaceHolder -> "placeholder_$index"
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
