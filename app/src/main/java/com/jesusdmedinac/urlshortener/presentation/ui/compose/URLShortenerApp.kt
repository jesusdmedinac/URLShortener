package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jesusdmedinac.urlshortener.ui.theme.URLShortenerTheme

@Composable
fun URLShortenerApp(name: String, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    URLShortenerTheme {
        URLShortenerApp("Android")
    }
}