package com.jesusdmedinac.urlshortener.domain.usecase

import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ShortenURLUseCaseImplTest {
    @MockK
    private lateinit var urlShortenerRepository: URLShortenerRepository

    private lateinit var shortenURLUseCaseImpl: ShortenURLUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        shortenURLUseCaseImpl = ShortenURLUseCaseImpl(
            urlShortenerRepository,
        )
    }

    @Test
    fun `invoke should call shortenUrl from urlShortenerRepository given urlToShorten is valid url`() =
        runTest {
            // Given
            val urlToShorten = "https://www.google.com"
            coEvery { urlShortenerRepository.shortenUrl(urlToShorten) } just runs

            // When
            shortenURLUseCaseImpl(urlToShorten)

            // Then
            coVerify { urlShortenerRepository.shortenUrl(urlToShorten) }
        }

    @Test
    fun `invoke should not call shortenUrl from urlShortenerRepository given urlToShorten is invalid url`() =
        runTest {
            // Given
            val urlToShorten = "invalid url"
            coEvery { urlShortenerRepository.shortenUrl(urlToShorten) } just runs

            // When
            shortenURLUseCaseImpl(urlToShorten)

            // Then
            coVerify(exactly = 0) { urlShortenerRepository.shortenUrl(urlToShorten) }
        }
}
