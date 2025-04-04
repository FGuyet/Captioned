package com.fguyet.captioned.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fguyet.captioned.presentation.nav.CaptionedNavHost
import com.fguyet.captioned.presentation.theme.CaptionedTheme

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaptionedTheme {
                val navController = rememberNavController()
                CaptionedNavHost(navController)
            }
        }
    }
}
