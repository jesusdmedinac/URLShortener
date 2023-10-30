package com.jesusdmedinac.urlshortener.domain.repository

import com.jesusdmedinac.urlshortener.data.local.URLShortenerLocalDataSource
import com.jesusdmedinac.urlshortener.data.remote.URLShortenerRemoteDataSource
import com.jesusdmedinac.urlshortener.domain.mapper.LocalShortenedURLToDomainShortenedURLMapper
import com.jesusdmedinac.urlshortener.domain.mapper.RemoteShortenedURLToLocalShortenedURLMapper
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface URLShortenerRepository {
    suspend fun shortenUrl(urlToShorten: String)
    suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>>
}

class URLShortenerRepositoryImpl(
    private val urlShortenerLocalDataSource: URLShortenerLocalDataSource,
    private val urlShortenerRemoteDataSource: URLShortenerRemoteDataSource,
    private val remoteShortenedURLToLocalShortenedURLMapper: RemoteShortenedURLToLocalShortenedURLMapper,
    private val localShortenedURLToDomainShortenedURLMapper: LocalShortenedURLToDomainShortenedURLMapper,
) : URLShortenerRepository {
    override suspend fun shortenUrl(urlToShorten: String) {
        val remoteShortenedURL = urlShortenerRemoteDataSource.shortenUrl(urlToShorten)
        val shortenedUrl = remoteShortenedURLToLocalShortenedURLMapper.map(remoteShortenedURL)
        urlShortenerLocalDataSource.saveShortenedURL(shortenedUrl)
    }

    override suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>> =
        urlShortenerLocalDataSource.getShortenedURLHistory()
            .map { shortenedURLHistory ->
                shortenedURLHistory.map(
                    localShortenedURLToDomainShortenedURLMapper::map,
                )
            }
}
