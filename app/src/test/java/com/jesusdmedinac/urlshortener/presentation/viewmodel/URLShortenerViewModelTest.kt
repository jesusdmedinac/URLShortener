package com.jesusdmedinac.urlshortener.presentation.viewmodel

import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.domain.usecase.GetShortenedURLHistoryUseCase
import com.jesusdmedinac.urlshortener.domain.usecase.ShortenURLUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
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
    fun `shortenURL should call invoke from shortenURLUseCase`() = runTest {
        // Given
        val urlToShorten = Random.nextInt().toString()
        coEvery { shortenURLUseCase(urlToShorten) } just runs
        urlShortenerViewModel.test(this, URLShortenerState()) {
            expectInitialState()

            // When
            containerHost.shortenURL(urlToShorten).join()

            // Then
            coVerify { shortenURLUseCase(urlToShorten) }
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
}
