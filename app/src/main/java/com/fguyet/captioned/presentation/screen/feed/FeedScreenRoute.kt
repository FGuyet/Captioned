package com.fguyet.captioned.presentation.screen.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.fguyet.captioned.core.commons.LaunchOnLifecycleEvent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FeedScreenRoute(
    modifier: Modifier = Modifier,
    onCapture: () -> Unit,
    viewModel: FeedViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchOnLifecycleEvent { event: Lifecycle.Event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> viewModel.refreshData()
            else -> {}
        }
    }

    val coroutineScope = rememberCoroutineScope()

    FeedScreen(
        modifier = modifier,
        onCapture = {
            coroutineScope.launch {
                viewModel.setLoading(true)
                onCapture()
            }
        },
        onRemindFriend = { user ->
            viewModel.remindFriend(user)
        },
        uiState = uiState
    )
}


