import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fguyet.captioned.domain.entity.Caption
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration

@Composable
internal fun CaptionHeader(
    caption: Caption?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f, fill = true)) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "\uD83D\uDCAC Today's caption",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .let {
                        if (caption == null) {
                            it
                                .shimmer()
                                .background(Color.Gray)
                        } else it
                    },
                text = caption?.text?.let { "“$it”" } ?: "",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        var remainingTime by remember {
            mutableStateOf<Duration?>(null)
        }

        LaunchedEffect(caption?.validity?.end) {
            while (isActive) {
                delay(1.seconds)

                remainingTime = caption?.let {
                    java.time.Duration.between(
                        ZonedDateTime.now(),
                        it.validity.end,
                    ).toKotlinDuration().takeUnless { duration ->
                        duration < Duration.ZERO
                    }
                }
            }
        }

        val remainingTimeStr by remember(caption?.validity?.end) {
            derivedStateOf {
                remainingTime?.formatToRemainingTime()
            }
        }

        Surface(
            modifier = Modifier
                .padding(start = 8.dp)
                .let {
                    if (remainingTimeStr == null) it.shimmer() else it
                },
            color = if (remainingTimeStr == null) Color.Gray else MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(8.dp),
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = "⏱️ ${remainingTimeStr ?: ""}",
                style = MaterialTheme.typography.titleMedium,
            )
        }
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