package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerViewModel

@Composable
fun URLShortenerApp() {
    val urlShortenerViewModel: URLShortenerViewModel = viewModel()
    val urlShortenerState by urlShortenerViewModel.container.stateFlow.collectAsState()
    Text(text = urlShortenerState.url)
}

@Preview(showBackground = true)
@Composable
fun URLShortenerAppPreview() {
    URLShortenerApp()
}
