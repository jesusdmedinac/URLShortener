package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.runtime.mutableStateOf
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerIntents

class HomeScreenEngine(
    private val urlShortenerIntents: URLShortenerIntents,
) {
    var homeScreenState = mutableStateOf(HomeScreenState())

    fun onURLToShortenChanged(urlToShorten: String) {
        homeScreenState.value = homeScreenState.value.copy(urlToShorten = urlToShorten)
    }

    fun onShortenURLClicked() {
        urlShortenerIntents.shortenURL(homeScreenState.value.urlToShorten)
    }

    data class HomeScreenState(
        val urlToShorten: String = "",
    )
}
