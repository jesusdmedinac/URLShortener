package com.jesusdmedinac.urlshortener.domain.usecase

import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import javax.inject.Inject

interface ShortenURLUseCase {
    suspend operator fun invoke(urlToShorten: String)
}
class ShortenURLUseCaseImpl @Inject constructor(
    private val urlShortenerRepository: URLShortenerRepository,
) : ShortenURLUseCase {
    override suspend fun invoke(urlToShorten: String) {
        urlShortenerRepository.shortenUrl(urlToShorten)
    }
}
