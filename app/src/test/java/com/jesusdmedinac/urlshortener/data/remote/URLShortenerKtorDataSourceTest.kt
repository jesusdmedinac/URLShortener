package com.jesusdmedinac.urlshortener.data.remote

import com.jesusdmedinac.urlshortener.data.remote.model.Links
import com.jesusdmedinac.urlshortener.data.remote.model.ShortenedURL
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import kotlin.random.Random

class URLShortenerKtorDataSourceTest {
    private lateinit var urlShortenerKtorDataSource: URLShortenerKtorDataSource

    @Test
    fun `shortenUrl should return expectedShortenedURL given httpResponse returns expectedResponse`() =
        runTest {
            // Given
            val expectedOriginalLink = Random.nextInt().toString()
            val expectedAlias = Random.nextInt().toString()
            val expectedShort = Random.nextInt().toString()
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel(
                        """
                    {
                      "alias": "$expectedAlias",
                      "_links": {
                        "self": "$expectedOriginalLink",
                        "short": "$expectedShort"
                      }
                    }
                        """.trimIndent(),
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }
            val httpClient = HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            this.ignoreUnknownKeys = true
                        },
                    )
                }
            }
            urlShortenerKtorDataSource = URLShortenerKtorDataSource(
                httpClient,
            )

            // When
            val result = urlShortenerKtorDataSource.shortenUrl(expectedOriginalLink)

            // Then
            val expectedShortenedURL = ShortenedURL(
                alias = expectedAlias,
                _links = Links(
                    self = expectedOriginalLink,
                    short = expectedShort,
                ),
            )
            assertThat(result, `is`(expectedShortenedURL))
        }
}
