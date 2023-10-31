package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.jesusdmedinac.urlshortener.domain.model.Links
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerIntents
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class HomeScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    private lateinit var urlShortenerState: URLShortenerState

    @MockK
    private lateinit var shortenedURL: ShortenedURL

    @MockK
    private lateinit var links: Links

    @MockK
    private lateinit var urlShortenerIntents: URLShortenerIntents

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery { urlShortenerIntents.getShortenedURLHistory() } returns mockk()
        every { links.self } returns Random.nextInt().toString()
        every { links.short } returns Random.nextInt().toString()
        every { shortenedURL.links } returns links
        every { urlShortenerState.shortenedURLHistory } returns listOf(
            shortenedURL,
        )
    }

    @Test
    fun composeContentShouldDisplayRecentlyShortenedURLsLabel() {
        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        composeTestRule.onNodeWithText("Recently shortened URLs").assertIsDisplayed()
    }

    @Test
    fun composeContentShouldCallGetShortenedURLHistoryFromURLShortenerIntents() {
        // Given
        coEvery { urlShortenerIntents.getShortenedURLHistory() } returns mockk()

        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        coVerify { urlShortenerIntents.getShortenedURLHistory() }
    }

    @Test
    fun composeContentShouldDisplayEnterURLToShortenPlaceholder() {
        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        composeTestRule.onNodeWithText("Enter URL to shorten").assertIsDisplayed()
    }

    @Test
    fun composeContentShouldDisplayShortenURLButton() {
        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Shorten URL").assertIsDisplayed()
    }

    @Test
    fun composeContentShouldDisplaySelfLinkFromShortenedURLHistoryItem() {
        // Given
        val self = Random.nextInt().toString()
        every { links.self } returns self
        every { shortenedURL.links } returns links
        every { urlShortenerState.shortenedURLHistory } returns listOf(
            shortenedURL,
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        composeTestRule.onNodeWithText(self).assertIsDisplayed()
    }

    @Test
    fun composeContentShouldDisplayShortLinkFromShortenedURLHistoryItem() {
        // Given
        val short = Random.nextInt().toString()
        every { links.short } returns short
        every { shortenedURL.links } returns links
        every { urlShortenerState.shortenedURLHistory } returns listOf(
            shortenedURL,
        )

        // When
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerIntents,
            )
        }

        // Then
        composeTestRule.onNodeWithText(short).assertIsDisplayed()
    }
}
