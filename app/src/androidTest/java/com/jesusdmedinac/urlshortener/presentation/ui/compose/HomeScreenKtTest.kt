package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerIntents
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerSideEffect
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    private lateinit var urlShortenerState: URLShortenerState

    @MockK
    private lateinit var urlShortenerSideEffect: URLShortenerSideEffect

    @MockK
    private lateinit var urlShortenerIntents: URLShortenerIntents

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun labelRecentlyShortenedURLsIsDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                urlShortenerState,
                urlShortenerSideEffect,
                urlShortenerIntents,
            )
        }

        composeTestRule.onNodeWithText("Recently shortened URLs").assertIsDisplayed()
    }
}
