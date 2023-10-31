package com.jesusdmedinac.urlshortener.data.remote

import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL
import com.jesusdmedinac.urlshortener.data.remote.model.URLToShorten
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

interface URLShortenerRemoteDataSource {
    suspend fun shortenUrl(link: String): ShortenedURL
}

class URLShortenerKtorDataSource @Inject constructor(
    private val httpClient: HttpClient,
) : URLShortenerRemoteDataSource {
    override suspend fun shortenUrl(link: String): ShortenedURL = httpClient
        .post(
            urlString = API_LINK_SHORTEN_URL,
            block = {
                contentType(ContentType.Application.Json)
                val body = URLToShorten(
                    url = link,
                )
                setBody(body)
            },
        )
        .body()

    companion object {
        const val API_LINK_SHORTEN_URL = "https://url-shortener-server.onrender.com/api/alias"
    }
}
