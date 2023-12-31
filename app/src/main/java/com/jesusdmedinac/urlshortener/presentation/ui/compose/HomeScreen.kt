package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.urlshortener.domain.model.Links
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerIntents
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerState
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun HomeScreen(
    urlShortenerState: URLShortenerState,
    urlShortenerIntents: URLShortenerIntents,
) {
    LaunchedEffect(Unit) {
        urlShortenerIntents.getShortenedURLHistory()
    }
    val homeScreenEngine = HomeScreenEngine(urlShortenerIntents)
    val homeScreenState by homeScreenEngine.homeScreenState
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            URLShortener(
                homeScreenState,
                homeScreenEngine,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.size(16.dp))
            ShortenedURLHistory(
                urlShortenerState,
                urlShortenerIntents,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .semantics { contentDescription = "Shortened URLs History" },
            )
        }
    }
}

@Composable
@Preview
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun HomeScreenPreview() {
    HomeScreen(
        urlShortenerState = URLShortenerState(
            shortenedURLHistory = listOf(
                ShortenedURL(
                    "",
                    Links(
                        self = "https://www.google.com",
                        short = "https://www.google.com",
                    ),
                ),
                ShortenedURL(
                    "",
                    Links(
                        self = "https://www.google.com",
                        short = "https://www.google.com",
                    ),
                ),
            ),
        ),
        urlShortenerIntents = DefaultURLShortenerIntents,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
private fun URLShortener(
    homeScreenState: HomeScreenEngine.HomeScreenState,
    homeScreenEngine: HomeScreenEngine,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = homeScreenState.urlToShorten,
            onValueChange = homeScreenEngine::onURLToShortenChanged,
            modifier = Modifier
                .weight(1f),
            placeholder = {
                Text(text = "Enter URL to shorten")
            },
        )
        IconButton(
            onClick = homeScreenEngine::onShortenURLClicked,
            modifier = Modifier.size(64.dp),
        ) {
            Icon(
                Icons.Outlined.Send,
                contentDescription = "Shorten URL",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun URLShortenerPreview() {
    URLShortener(
        homeScreenState = HomeScreenEngine.HomeScreenState(
            urlToShorten = "https://www.google.com",
        ),
        homeScreenEngine = HomeScreenEngine(DefaultURLShortenerIntents),
    )
}

@Composable
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
private fun ShortenedURLHistory(
    urlShortenerState: URLShortenerState,
    urlShortenerIntents: URLShortenerIntents,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Text(
                text = "Recently shortened URLs",
                modifier = Modifier
                    .padding(16.dp),
                fontWeight = FontWeight.Bold,
            )
        }
        items(urlShortenerState.shortenedURLHistory) { shortenedURL: ShortenedURL ->
            ShortenedURLItem(
                shortenedURL = shortenedURL,
                urlShortenerIntents = urlShortenerIntents,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun ShortenedURLHistoryPreview() {
    Column {
        ShortenedURLHistory(
            urlShortenerState = URLShortenerState(
                shortenedURLHistory = listOf(
                    ShortenedURL(
                        "",
                        Links(
                            self = "https://www.google.com",
                            short = "https://www.google.com",
                        ),
                    ),
                    ShortenedURL(
                        "",
                        Links(
                            self = "https://www.google.com",
                            short = "https://www.google.com",
                        ),
                    ),
                ),
            ),
            urlShortenerIntents = DefaultURLShortenerIntents,
        )
    }
}

@Composable
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
private fun ShortenedURLItem(
    shortenedURL: ShortenedURL,
    urlShortenerIntents: URLShortenerIntents,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                urlShortenerIntents.onShortenedURLClicked(shortenedURL)
            },
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = shortenedURL.links.self)
        Text(text = shortenedURL.links.short)
        Spacer(modifier = Modifier.size(8.dp))
    }
    Divider()
}

@Composable
@Preview(showBackground = true)
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun ShortenedURLItemPreview() {
    ShortenedURLItem(
        shortenedURL = ShortenedURL(
            "",
            Links(
                self = "https://www.google.com",
                short = "https://www.google.com",
            ),
        ),
        urlShortenerIntents = DefaultURLShortenerIntents,
    )
}

/**
 * This is a default implementation of [URLShortenerIntents] that does nothing.
 * This is useful for previewing the UI.
 */
object DefaultURLShortenerIntents : URLShortenerIntents {
    override fun shortenURL(urlToShorten: String): Job {
        TODO()
    }

    override fun getShortenedURLHistory(): Job {
        TODO()
    }

    override fun onShortenedURLClicked(shortenedURL: ShortenedURL): Job {
        TODO()
    }
}
