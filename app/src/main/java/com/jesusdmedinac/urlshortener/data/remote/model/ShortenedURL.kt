package com.jesusdmedinac.urlshortener.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenedURL(
    val alias: String,
    @Suppress("ConstructorParameterNaming", "PropertyName") // Suppress because of the API response
    val _links: Links,
)

@Serializable
data class Links(
    val self: String,
    val short: String,
)
