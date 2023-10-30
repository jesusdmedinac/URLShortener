package com.jesusdmedinac.urlshortener.data.local

import com.jesusdmedinac.urlshortener.data.local.model.ShortenedUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface URLShortenerLocalDataSource {
    suspend fun saveShortenedUrl(shortenedUrl: ShortenedUrl)
    suspend fun getShortenedUrlHistory(): Flow<List<ShortenedUrl>>
}

class URLShortenerInMemoryDataSource : URLShortenerLocalDataSource {
    private val shortenedUrls: MutableStateFlow<List<ShortenedUrl>> = MutableStateFlow(emptyList())

    override suspend fun saveShortenedUrl(shortenedUrl: ShortenedUrl) {
        shortenedUrls.value = shortenedUrls.value + shortenedUrl
    }

    override suspend fun getShortenedUrlHistory(): Flow<List<ShortenedUrl>> =
        shortenedUrls
}
