package com.jesusdmedinac.urlshortener.domain.repository

import com.jesusdmedinac.urlshortener.data.local.URLShortenerLocalDataSource
import com.jesusdmedinac.urlshortener.data.remote.URLShortenerRemoteDataSource
import com.jesusdmedinac.urlshortener.domain.mapper.LocalShortenedURLToDomainShortenedURLMapper
import com.jesusdmedinac.urlshortener.domain.mapper.RemoteShortenedURLToLocalShortenedURLMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import kotlin.random.Random
import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL as LocalShortenedURL
import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL as RemoteShortenedURL
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL as DomainShortenedURL

@OptIn(ExperimentalCoroutinesApi::class)
class URLShortenerRepositoryImplTest {
    @MockK
    private lateinit var urlShortenerLocalDataSource: URLShortenerLocalDataSource

    @MockK
    private lateinit var urlShortenerRemoteDataSource: URLShortenerRemoteDataSource

    @MockK
    private lateinit var remoteShortenedURLToLocalShortenedURLMapper: RemoteShortenedURLToLocalShortenedURLMapper

    @MockK
    private lateinit var localShortenedURLToDomainShortenedURLMapper: LocalShortenedURLToDomainShortenedURLMapper

    private lateinit var urlShortenerRepositoryImpl: URLShortenerRepositoryImpl

    @MockK
    private lateinit var domainShortenedURL: DomainShortenedURL

    @MockK
    private lateinit var remoteShortenedURL: RemoteShortenedURL

    @MockK
    private lateinit var localShortenedURL: LocalShortenedURL

    private lateinit var coroutineDispatcher: TestDispatcher

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coroutineDispatcher = UnconfinedTestDispatcher()
        urlShortenerRepositoryImpl = URLShortenerRepositoryImpl(
            urlShortenerLocalDataSource,
            urlShortenerRemoteDataSource,
            remoteShortenedURLToLocalShortenedURLMapper,
            localShortenedURLToDomainShortenedURLMapper,
            coroutineDispatcher,
        )
    }

    @Test
    fun `shortenUrl should return domainShortenedURL`() = runTest {
        // Given
        val urlToShorten = Random.nextInt().toString()
        coEvery { urlShortenerRemoteDataSource.shortenUrl(urlToShorten) } returns remoteShortenedURL
        coEvery { urlShortenerLocalDataSource.saveShortenedURL(localShortenedURL) } just runs
        every { remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL) } returns localShortenedURL

        // When
        urlShortenerRepositoryImpl.shortenUrl(urlToShorten)

        // Then
        coVerifySequence {
            urlShortenerRemoteDataSource.shortenUrl(urlToShorten)
            urlShortenerLocalDataSource.saveShortenedURL(localShortenedURL)
        }
    }

    @Test
    fun `getShortenedURLHistory should return localShortenedURL`() = runTest {
        // Given
        coEvery { urlShortenerLocalDataSource.getShortenedURLHistory() } returns flowOf(
            listOf(
                localShortenedURL,
            ),
        )
        every { localShortenedURLToDomainShortenedURLMapper.map(localShortenedURL) } returns domainShortenedURL

        // When
        val result = urlShortenerRepositoryImpl.getShortenedURLHistory()
            .first()
            .first()

        // Then
        assertThat(domainShortenedURL, `is`(result))
    }
}
