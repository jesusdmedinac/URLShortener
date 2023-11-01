package com.jesusdmedinac.urlshortener.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.launchActivity
import com.jesusdmedinac.urlshortener.data.remote.model.URLToShorten
import com.jesusdmedinac.urlshortener.di.HttpClientModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(HttpClientModule::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Module
    @InstallIn(SingletonComponent::class)
    object HttpClientModule {
        @Provides
        fun provideHttpClient(): HttpClient {
            val mockEngine = MockEngine { request ->
                val urlToShorten: URLToShorten =
                    Json.decodeFromStream(request.body.toByteArray().inputStream())
                val alias = urlToShorten.url.length.toString()
                respond(
                    content = ByteReadChannel(
                        """
                    {
                      "alias": "$alias",
                      "_links": {
                        "self": "${urlToShorten.url}",
                        "short": "https://url-shortener-server.onrender.com/api/alias/$alias"
                      }
                    }
                        """.trimIndent(),
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }
            return HttpClient(mockEngine) {
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
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun performClickOnNodeWithContentDescriptionShorten_URLShouldDisplayShortURLGivenURLTextInputIsPerformedOnNodeWithTextEnter_URL_to_shorten() {
        // Given
        launchActivity<MainActivity>()
        val urlToShorten = "https://www.google.com"
        composeTestRule
            .onNodeWithText("Enter URL to shorten")
            .performTextInput(urlToShorten)

        // When
        composeTestRule
            .onNodeWithContentDescription("Shorten URL")
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText("https://url-shortener-server.onrender.com/api/alias/${urlToShorten.length}")
            .assertIsDisplayed()
    }

    @Test
    fun performClickOnNodeWithContentDescriptionShorten_URLShouldDisplaySelfURLGivenURLTextInputIsPerformedOnNodeWithTextEnter_URL_to_shorten() {
        // Given
        launchActivity<MainActivity>()
        val urlToShorten = "https://www.google.com"
        composeTestRule
            .onNodeWithText("Enter URL to shorten")
            .performTextInput(urlToShorten)

        // When
        composeTestRule
            .onNodeWithContentDescription("Shorten URL")
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText(urlToShorten)
            .assertIsDisplayed()
    }
}
