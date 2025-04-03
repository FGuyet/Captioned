package com.fguyet.captioned.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fguyet.captioned.presentation.screen.welcome.WelcomeScreen


@Composable
internal fun CaptionedNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.WELCOME) {
        composable(NavRoute.WELCOME) {
            WelcomeScreen(
                onSignIn = {
                    navController.navigate(NavRoute.CAPTION) {
                        popUpTo(NavRoute.WELCOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = NavRoute.CAPTION) {}
        composable(route = NavRoute.FEED) {

        }
    }
}

