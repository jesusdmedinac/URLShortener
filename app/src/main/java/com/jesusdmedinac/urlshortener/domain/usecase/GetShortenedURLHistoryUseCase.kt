package com.jesusdmedinac.urlshortener.domain.usecase

import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetShortenedURLHistoryUseCase {
    suspend operator fun invoke(): Flow<List<ShortenedURL>>
}

class GetShortenedURLHistoryUseCaseImpl @Inject constructor(
    private val urlShortenerRepository: URLShortenerRepository,
) : GetShortenedURLHistoryUseCase {
    override suspend fun invoke(): Flow<List<ShortenedURL>> {
        return urlShortenerRepository.getShortenedURLHistory()
    }
}
