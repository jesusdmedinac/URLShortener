package com.jesusdmedinac.urlshortener.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.domain.usecase.GetShortenedURLHistoryUseCase
import com.jesusdmedinac.urlshortener.domain.usecase.ShortenURLUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class URLShortenerViewModel @Inject constructor(
    private val shortenURLUseCase: ShortenURLUseCase,
    private val getShortenedURLHistoryUseCase: GetShortenedURLHistoryUseCase,
) :
    ViewModel(),
    ContainerHost<URLShortenerState, URLShortenerSideEffect>,
    URLShortenerIntents {
    override val container: Container<URLShortenerState, URLShortenerSideEffect> =
        viewModelScope.container(URLShortenerState())

    override fun shortenURL(urlToShorten: String): Job = intent {
        shortenURLUseCase(urlToShorten)
            .onFailure { error ->
                reduce {
                    state.copy(error = error)
                }
            }
            .onSuccess {
                reduce {
                    state.copy(error = null)
                }
            }
    }

    override fun getShortenedURLHistory(): Job = intent {
        getShortenedURLHistoryUseCase()
            .collect { shortenedURLHistory ->
                reduce {
                    state.copy(shortenedURLHistory = shortenedURLHistory)
                }
            }
    }

    override fun onShortenedURLClicked(shortenedURL: ShortenedURL) = intent {
        postSideEffect(URLShortenerSideEffect.OnShortenedURLClicked(shortenedURL))
        delay(DELAY_TIME_BETWEEN_SIDE_EFFECTS)
        postSideEffect(URLShortenerSideEffect.Idle)
    }

    companion object {
        private const val DELAY_TIME_BETWEEN_SIDE_EFFECTS = 500L
    }
}

data class URLShortenerState(
    val shortenedURLHistory: List<ShortenedURL> = emptyList(),
    val error: Throwable? = null,
)

sealed class URLShortenerSideEffect {
    data class OnShortenedURLClicked(val shortenedURL: ShortenedURL) : URLShortenerSideEffect()

    object Idle : URLShortenerSideEffect()
}

interface URLShortenerIntents {
    fun shortenURL(urlToShorten: String): Job
    fun getShortenedURLHistory(): Job
    fun onShortenedURLClicked(shortenedURL: ShortenedURL): Job
}
