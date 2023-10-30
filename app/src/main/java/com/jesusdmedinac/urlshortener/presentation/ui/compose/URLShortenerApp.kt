package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerViewModel

@Composable
fun URLShortenerApp() {
    val urlShortenerViewModel: URLShortenerViewModel = hiltViewModel()
    val urlShortenerState by urlShortenerViewModel.container.stateFlow.collectAsState()
    Text(text = urlShortenerState.shortenedURLHistory.first().alias)
}

@Preview(showBackground = true)
@Composable
fun URLShortenerAppPreview() {
    URLShortenerApp()
}
