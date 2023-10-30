package com.jesusdmedinac.urlshortener.domain.usecase

import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class GetShortenedURLHistoryUseCaseImplTest {
    @MockK
    private lateinit var urlShortenerRepository: URLShortenerRepository

    private lateinit var getShortenedURLHistoryUseCaseImpl: GetShortenedURLHistoryUseCaseImpl

    @MockK
    private lateinit var shortenedURL: ShortenedURL

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getShortenedURLHistoryUseCaseImpl = GetShortenedURLHistoryUseCaseImpl(
            urlShortenerRepository,
        )
    }

    @Test
    fun `invoke should return expectedShortenedURL given urlShortenerRepository returns expectedShortenedURL`() =
        runTest {
            // Given
            coEvery { urlShortenerRepository.getShortenedURLHistory() } returns flowOf(
                listOf(
                    shortenedURL,
                ),
            )

            // When
            val result = getShortenedURLHistoryUseCaseImpl()
                .first()
                .first()

            // Then
            assertThat(result, `is`(shortenedURL))
        }
}
