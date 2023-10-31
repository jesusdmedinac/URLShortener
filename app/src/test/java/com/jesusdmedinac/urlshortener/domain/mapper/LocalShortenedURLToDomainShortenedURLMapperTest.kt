package com.jesusdmedinac.urlshortener.domain.mapper

import com.jesusdmedinac.urlshortener.data.local.model.Links
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL as LocalShortenedURL

class LocalShortenedURLToDomainShortenedURLMapperTest {
    private lateinit var localShortenedURLToDomainShortenedURLMapper: LocalShortenedURLToDomainShortenedURLMapper

    private lateinit var localShortenedURL: LocalShortenedURL

    @Before
    fun setUp() {
        localShortenedURL = LocalShortenedURL(
            alias = Random.nextInt().toString(),
            links = Links(
                short = Random.nextInt().toString(),
                self = Random.nextInt().toString(),
            ),
        )
        localShortenedURLToDomainShortenedURLMapper = LocalShortenedURLToDomainShortenedURLMapper()
    }

    @Test
    fun `map should return DomainShortenedUrl with expectedAlias`() {
        // Given
        val expectedAlias = Random.nextInt().toString()
        localShortenedURL = localShortenedURL.copy(
            alias = expectedAlias,
        )

        // When
        val result = localShortenedURLToDomainShortenedURLMapper.map(localShortenedURL)
            .alias

        // Then
        assertThat(result, `is`(expectedAlias))
    }

    @Test
    fun `map should return DomainShortenedUrl with expectedShortUrl`() {
        // Given
        val expectedShortUrl = Random.nextInt().toString()
        localShortenedURL = localShortenedURL.copy(
            links = localShortenedURL.links.copy(
                short = expectedShortUrl,
            ),
        )

        // When
        val result = localShortenedURLToDomainShortenedURLMapper.map(localShortenedURL)
            .links
            .short

        // Then
        assertThat(result, `is`(expectedShortUrl))
    }

    @Test
    fun `map should return DomainShortenedUrl with expectedSelfUrl`() {
        // Given
        val expectedSelfUrl = Random.nextInt().toString()
        localShortenedURL = localShortenedURL.copy(
            links = localShortenedURL.links.copy(
                self = expectedSelfUrl,
            ),
        )

        // When
        val result = localShortenedURLToDomainShortenedURLMapper.map(localShortenedURL)
            .links
            .self

        // Then
        assertThat(result, `is`(expectedSelfUrl))
    }
}
