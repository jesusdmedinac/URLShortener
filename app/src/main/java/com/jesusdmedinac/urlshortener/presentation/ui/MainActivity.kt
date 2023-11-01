package com.jesusdmedinac.urlshortener.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jesusdmedinac.urlshortener.presentation.ui.compose.URLShortenerApp
import com.jesusdmedinac.urlshortener.presentation.ui.theme.URLShortenerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            URLShortenerTheme {
                URLShortenerApp()
            }
        }
    }
}
