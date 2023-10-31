package com.jesusdmedinac.urlshortener.presentation.viewmodel

import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.domain.usecase.GetShortenedURLHistoryUseCase
import com.jesusdmedinac.urlshortener.domain.usecase.ShortenURLUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.orbitmvi.orbit.test.test
import kotlin.random.Random

class URLShortenerViewModelTest {
    @MockK
    private lateinit var shortenURLUseCase: ShortenURLUseCase

    @MockK
    private lateinit var getShortenedURLHistoryUseCase: GetShortenedURLHistoryUseCase

    private lateinit var urlShortenerViewModel: URLShortenerViewModel

    @MockK
    private lateinit var shortenedURL: ShortenedURL

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        urlShortenerViewModel = URLShortenerViewModel(
            shortenURLUseCase,
            getShortenedURLHistoryUseCase,
        )
    }

    @Test
    fun `shortenURL should reduce URLShortenerState with error given invoke from shortenURLUseCase returns error`() =
        runTest {
            // Given
            val urlToShorten = Random.nextInt().toString()
            val error = Throwable()
            coEvery { shortenURLUseCase(urlToShorten) } returns Result.failure(error)
            urlShortenerViewModel.test(this, URLShortenerState()) {
                expectInitialState()

                // When
                containerHost.shortenURL(urlToShorten).join()

                // Then
                expectState { copy(error = error) }
            }
        }

    @Test
    fun `shortenURL should reduce URLShortenerState with no error given invoke from shortenURLUseCase returns error and then success`() =
        runTest {
            // Given
            val urlToShorten = Random.nextInt().toString()
            val error = Throwable()
            coEvery { shortenURLUseCase(urlToShorten) } returns Result.failure(error) andThen Result.success(
                Unit,
            )
            urlShortenerViewModel.test(this, URLShortenerState()) {
                expectInitialState()
                containerHost.shortenURL(urlToShorten).join()

                // When
                containerHost.shortenURL(urlToShorten).join()

                // Then
                expectState { copy(error = error) }
                expectState { copy(error = null) }
            }
        }

    @Test
    fun `getShortenedURLHistory should reduce URLShortenerState with shortenedURLHistory given invoke from getShortenedURLHistoryUseCase returns shortenedURLHistory`() =
        runTest {
            // Given
            val shortenedURLHistory = listOf(shortenedURL)
            coEvery { getShortenedURLHistoryUseCase() } returns flowOf(shortenedURLHistory)
            urlShortenerViewModel.test(this, URLShortenerState()) {
                expectInitialState()

                // When
                containerHost.getShortenedURLHistory().join()

                // Then
                expectState { copy(shortenedURLHistory = shortenedURLHistory) }
            }
        }

    @Test
    fun `onShortenedURLClicked should call onShortenedURLClicked from URLShortenerIntents`() =
        runTest {
            // Given
            urlShortenerViewModel.test(this, URLShortenerState()) {
                expectInitialState()

                // When
                containerHost.onShortenedURLClicked(shortenedURL)

                // Then
                expectSideEffect(URLShortenerSideEffect.OnShortenedURLClicked(shortenedURL))
                expectSideEffect(URLShortenerSideEffect.Idle)
            }
        }
}
