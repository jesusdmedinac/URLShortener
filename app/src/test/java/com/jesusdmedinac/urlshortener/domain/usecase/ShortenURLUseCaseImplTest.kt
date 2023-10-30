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
    fun `invoke should call shortenUrl from urlShortenerRepository`() = runTest {
        // Given
        val expectedUrlToShorten = "https://www.google.com"
        coEvery { urlShortenerRepository.shortenUrl(expectedUrlToShorten) } just runs

        // When
        shortenURLUseCaseImpl.invoke(expectedUrlToShorten)

        // Then
        coVerify { urlShortenerRepository.shortenUrl(expectedUrlToShorten) }
    }
}
