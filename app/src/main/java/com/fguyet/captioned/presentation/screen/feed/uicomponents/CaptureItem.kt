package com.fguyet.captioned.presentation.screen.feed.uicomponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fguyet.captioned.R
import com.fguyet.captioned.presentation.theme.CaptionedTheme

@Composable
internal fun CaptureItem(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int,
    userName: String,
    isHidden: Boolean,
    isLiked: Boolean,
    likesCount: Int? = null,
    canLike: Boolean = true,
    onLikeChange: (Boolean) -> Unit = {},
    onImageLoaded: () -> Unit = {},
) {
    Box(
        modifier = if (canLike) modifier.pointerInput(isLiked) {
            detectTapGestures(
                onDoubleTap = { onLikeChange(!isLiked) },
            )
        }
        else modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .let {
                    if (isHidden) it.blur(
                        radiusX = 16.dp,
                        radiusY = 16.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    ) else it
                },
            model = imageResId,
            onSuccess = { onImageLoaded() },
            contentScale = ContentScale.Crop,
            contentDescription = "${userName}'s take on today's caption",
            colorFilter = ColorFilter.tint(Color.DarkGray, blendMode = BlendMode.Darken).takeIf { isHidden }
        )

        if (isHidden) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = R.drawable.app_logo,
                contentDescription = "Hidding layer",
            )
        }

        Surface(
            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = userName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        if (!isHidden) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .align(Alignment.BottomEnd)
                    .clickable(enabled = canLike) { onLikeChange(!isLiked) },
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    likesCount?.let {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = it.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    AsyncImage(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(8.dp),
                        model = if (isLiked) R.drawable.ic_filled_heart else R.drawable.ic_empty_heart,
                        contentDescription = "Hidding layer",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun CaptureItemPreview() {
    CaptionedTheme {
        CaptureItem(
            modifier = Modifier.size(300.dp),
            imageResId = R.drawable.organized_chaos_1,
            userName = "User Name",
            isHidden = false,
            isLiked = false,
        )
    }
}

@Preview
@Composable
internal fun LikedCaptureItemPreview() {
    CaptionedTheme {
        CaptureItem(
            modifier = Modifier.size(300.dp),
            imageResId = R.drawable.organized_chaos_1,
            userName = "User Name",
            isHidden = false,
            isLiked = true,
        )
    }
}

@Preview
@Composable
internal fun HiddenCaptureItemPreview() {
    CaptionedTheme {
        CaptureItem(
            modifier = Modifier.size(300.dp),
            imageResId = R.drawable.organized_chaos_1,
            userName = "User Name",
            isHidden = true,
            isLiked = false,
        )
    }
}