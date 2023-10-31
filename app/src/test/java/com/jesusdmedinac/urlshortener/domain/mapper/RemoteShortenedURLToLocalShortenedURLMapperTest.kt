package com.jesusdmedinac.urlshortener.domain.mapper

import com.jesusdmedinac.urlshortener.data.remote.model.Links
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL as RemoteShortenedURL

class RemoteShortenedURLToLocalShortenedURLMapperTest {
    private lateinit var remoteShortenedURLToLocalShortenedURLMapper: RemoteShortenedURLToLocalShortenedURLMapper

    private lateinit var remoteShortenedURL: RemoteShortenedURL

    @Before
    fun setUp() {
        remoteShortenedURL = RemoteShortenedURL(
            alias = Random.nextInt().toString(),
            _links = Links(
                short = Random.nextInt().toString(),
                self = Random.nextInt().toString(),
            ),
        )
        remoteShortenedURLToLocalShortenedURLMapper = RemoteShortenedURLToLocalShortenedURLMapper()
    }

    @Test
    fun `map should return LocalShortenedUrl with expectedAlias`() {
        // Given
        val expectedAlias = Random.nextInt().toString()
        remoteShortenedURL = remoteShortenedURL.copy(
            alias = expectedAlias,
        )

        // When
        val result = remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL)
            .alias

        // Then
        assertThat(result, `is`(expectedAlias))
    }

    @Test
    fun `map should return LocalShortenedUrl with expectedShortUrl`() {
        // Given
        val expectedShortUrl = Random.nextInt().toString()
        remoteShortenedURL = remoteShortenedURL.copy(
            _links = remoteShortenedURL._links.copy(
                short = expectedShortUrl,
            ),
        )

        // When
        val result = remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL)
            .links
            .short

        // Then
        assertThat(result, `is`(expectedShortUrl))
    }

    @Test
    fun `map should return LocalShortenedUrl with expectedSelfUrl`() {
        // Given
        val expectedSelfUrl = Random.nextInt().toString()
        remoteShortenedURL = remoteShortenedURL.copy(
            _links = remoteShortenedURL._links.copy(
                self = expectedSelfUrl,
            ),
        )

        // When
        val result = remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL)
            .links
            .self

        // Then
        assertThat(result, `is`(expectedSelfUrl))
    }
}
