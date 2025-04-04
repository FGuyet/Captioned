package com.fguyet.captioned.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fguyet.captioned.presentation.screen.feed.FeedScreenRoute
import com.fguyet.captioned.presentation.screen.welcome.WelcomeScreenRoute


@Composable
internal fun CaptionedNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.WELCOME) {
        composable(NavRoute.WELCOME) {
            WelcomeScreenRoute(
                onSignedIn = {
                    navController.navigate(NavRoute.FEED) {
                        popUpTo(NavRoute.WELCOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = NavRoute.FEED) {
            FeedScreenRoute(
                onCapture = {
                    navController.navigate(NavRoute.CAPTURE) {
                        popUpTo(NavRoute.FEED) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(route = NavRoute.CAPTURE) {}
    }
}

