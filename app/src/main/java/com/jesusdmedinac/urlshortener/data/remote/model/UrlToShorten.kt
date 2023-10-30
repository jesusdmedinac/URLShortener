package com.jesusdmedinac.urlshortener.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class URLToShorten(
    val url: String,
)
