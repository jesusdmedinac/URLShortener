package com.jesusdmedinac.urlshortener.domain.mapper

import com.jesusdmedinac.urlshortener.data.local.model.Links
import javax.inject.Inject
import com.jesusdmedinac.urlshortener.data.local.model.ShortenedURL as LocalShortenedURL
import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL as RemoteShortenedURL

class RemoteShortenedURLToLocalShortenedURLMapper @Inject constructor() {
    fun map(input: RemoteShortenedURL): LocalShortenedURL = LocalShortenedURL(
        input.alias,
        Links(
            self = input._links.self,
            short = input._links.short,
        ),
    )
}
