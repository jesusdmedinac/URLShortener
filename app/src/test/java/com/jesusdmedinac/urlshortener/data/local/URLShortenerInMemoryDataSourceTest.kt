package com.jesusdmedinac.urlshortener.data.local

import com.jesusdmedinac.urlshortener.data.local.model.ShortenedUrl
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

class URLShortenerInMemoryDataSourceTest {
    @MockK
    private lateinit var shortenedUrl: ShortenedUrl

    private lateinit var urlShortenerInMemoryDataSource: URLShortenerInMemoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        urlShortenerInMemoryDataSource = URLShortenerInMemoryDataSource()
    }

    @Test
    fun `getShortenedUrlHistory should return expectedShortenedUrlHistory given saveShortenedUrl was called with URLToShorten`() =
        runTest {
            // Given
            urlShortenerInMemoryDataSource.saveShortenedUrl(shortenedUrl)

            // When
            val result = urlShortenerInMemoryDataSource.getShortenedUrlHistory()
                .first()
                .first()

            // Then
            assertThat(result, `is`(shortenedUrl))
        }
}
