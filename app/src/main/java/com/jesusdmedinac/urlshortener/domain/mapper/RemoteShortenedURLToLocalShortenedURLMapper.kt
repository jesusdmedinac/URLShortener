package com.jesusdmedinac.urlshortener.domain.mapper

import javax.inject.Inject
import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL as LocalShortenedURL
import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL as RemoteShortenedURL

class RemoteShortenedURLToLocalShortenedURLMapper @Inject constructor() {
    fun map(input: RemoteShortenedURL): LocalShortenedURL = TODO()
}
