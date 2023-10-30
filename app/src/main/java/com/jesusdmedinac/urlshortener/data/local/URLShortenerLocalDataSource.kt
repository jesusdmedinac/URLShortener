package com.jesusdmedinac.urlshortener.data.local

import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface URLShortenerLocalDataSource {
    suspend fun saveShortenedURL(shortenedUrl: ShortenedURL)
    suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>>
}

class URLShortenerInMemoryDataSource @Inject constructor() : URLShortenerLocalDataSource {
    private val shortenedUrls: MutableStateFlow<List<ShortenedURL>> = MutableStateFlow(emptyList())

    override suspend fun saveShortenedURL(shortenedUrl: ShortenedURL) {
        shortenedUrls.value = shortenedUrls.value + shortenedUrl
    }

    override suspend fun getShortenedURLHistory(): Flow<List<ShortenedURL>> =
        shortenedUrls
}
