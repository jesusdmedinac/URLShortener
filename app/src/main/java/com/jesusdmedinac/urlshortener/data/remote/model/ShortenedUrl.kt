package com.jesusdmedinac.urlshortener.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenedUrl(
    val alias: String,
    val _links: Links,
)

@Serializable
data class Links(
    val self: String,
    val short: String,
)
