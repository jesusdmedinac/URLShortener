package com.jesusdmedinac.urlshortener.domain.model

data class ShortenedURL(
    val alias: String,
    val links: Links,
)

data class Links(
    val self: String,
    val short: String,
)
