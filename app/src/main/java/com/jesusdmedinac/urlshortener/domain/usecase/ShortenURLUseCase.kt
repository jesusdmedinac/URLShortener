package com.jesusdmedinac.urlshortener.domain.usecase

import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import javax.inject.Inject

interface ShortenURLUseCase {
    suspend operator fun invoke(urlToShorten: String): Result<Unit>
}

class ShortenURLUseCaseImpl @Inject constructor(
    private val urlShortenerRepository: URLShortenerRepository,
) : ShortenURLUseCase {
    override suspend fun invoke(urlToShorten: String): Result<Unit> =
        if (urlToShorten.matches(Regex(URL_REGEX_PATTERN)).not()) {
            Result.failure(IllegalArgumentException("Invalid URL"))
        } else {
            urlShortenerRepository.shortenUrl(urlToShorten)
            Result.success(Unit)
        }

    companion object {
        const val URL_REGEX_PATTERN =
            "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    }
}
