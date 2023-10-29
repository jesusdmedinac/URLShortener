package com.jesusdmedinac.urlshortener.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import javax.inject.Inject

@HiltViewModel
class URLShortenerViewModel @Inject constructor() :
    ViewModel(),
    ContainerHost<URLShortenerState, URLShortenerSideEffect> {
    override val container: Container<URLShortenerState, URLShortenerSideEffect> =
        viewModelScope.container(URLShortenerState())
}

data class URLShortenerState(
    val url: String = "https://www.google.com",
)

sealed class URLShortenerSideEffect {
    object OpenURL : URLShortenerSideEffect()
}
