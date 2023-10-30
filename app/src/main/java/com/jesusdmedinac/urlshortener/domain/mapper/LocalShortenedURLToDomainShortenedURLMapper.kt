package com.jesusdmedinac.urlshortener.domain.mapper

import javax.inject.Inject
import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL as LocalShortenedURL
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL as DomainShortenedURL

class LocalShortenedURLToDomainShortenedURLMapper @Inject constructor() {
    fun map(input: LocalShortenedURL): DomainShortenedURL = TODO()
}
