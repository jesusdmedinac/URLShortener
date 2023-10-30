package com.jesusdmedinac.urlshortener.domain.repository

import com.jesusdmedinac.urlshortener.data.local.URLShortenerLocalDataSource
import com.jesusdmedinac.urlshortener.data.remote.URLShortenerRemoteDataSource
import com.jesusdmedinac.urlshortener.domain.mapper.LocalShortenedURLToDomainShortenedURLMapper
import com.jesusdmedinac.urlshortener.domain.mapper.RemoteShortenedURLToLocalShortenedURLMapper
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

interface URLShortenerRepository {
    suspend fun shortenUrl(urlToShorten: String)
    suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>>
}

class URLShortenerRepositoryImpl @Inject constructor(
    private val urlShortenerLocalDataSource: URLShortenerLocalDataSource,
    private val urlShortenerRemoteDataSource: URLShortenerRemoteDataSource,
    private val remoteShortenedURLToLocalShortenedURLMapper: RemoteShortenedURLToLocalShortenedURLMapper,
    private val localShortenedURLToDomainShortenedURLMapper: LocalShortenedURLToDomainShortenedURLMapper,
    @Named("io-dispatcher")
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : URLShortenerRepository {
    override suspend fun shortenUrl(urlToShorten: String) {
        withContext(coroutineDispatcher) {
            val remoteShortenedURL = urlShortenerRemoteDataSource.shortenUrl(urlToShorten)
            val shortenedUrl = remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL)
            urlShortenerLocalDataSource.saveShortenedURL(shortenedUrl)
        }
    }

    override suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>> =
        withContext(coroutineDispatcher) {
            urlShortenerLocalDataSource.getShortenedURLHistory()
                .map { shortenedURLHistory ->
                    shortenedURLHistory.map(
                        localShortenedURLToDomainShortenedURLMapper::map,
                    )
                }
        }
}
