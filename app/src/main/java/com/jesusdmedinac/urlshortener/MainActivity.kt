package com.jesusdmedinac.urlshortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jesusdmedinac.urlshortener.presentation.ui.compose.URLShortenerApp
import com.jesusdmedinac.urlshortener.ui.theme.URLShortenerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            URLShortenerTheme {
                URLShortenerApp("Android")
            }
        }
    }
}
