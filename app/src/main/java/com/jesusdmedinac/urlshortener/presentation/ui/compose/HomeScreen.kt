package com.jesusdmedinac.urlshortener.presentation.ui.compose

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesusdmedinac.urlshortener.domain.model.Links
import com.jesusdmedinac.urlshortener.domain.model.ShortenedURL
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerIntents
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerSideEffect
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerState
import com.jesusdmedinac.urlshortener.ui.theme.URLShortenerTheme
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    urlShortenerState: URLShortenerState,
    urlShortenerSideEffect: URLShortenerSideEffect,
    urlShortenerIntents: URLShortenerIntents,
) {
    LaunchedEffect(Unit) {
        urlShortenerIntents.getShortenedURLHistory()
    }
    val homeScreenEngine = HomeScreenEngine(urlShortenerIntents)
    val homeScreenState by remember {
        homeScreenEngine.homeScreenState
    }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = shortenedURL.links.self)
                        Text(text = shortenedURL.links.short)
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                    Divider()
                }
            }
        }
    }
}

class HomeScreenEngine(
    private val urlShortenerIntents: URLShortenerIntents,
) {
    var homeScreenState = mutableStateOf(HomeScreenState())

    fun onURLToShortenChanged(urlToShorten: String) {
        homeScreenState.value = homeScreenState.value.copy(urlToShorten = urlToShorten)
    }

    fun onShortenURLClicked() {
        urlShortenerIntents.shortenURL(homeScreenState.value.urlToShorten)
    }

    data class HomeScreenState(
        val urlToShorten: String = "",
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    URLShortenerTheme {
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
            urlShortenerSideEffect = URLShortenerSideEffect.Idle,
            urlShortenerIntents = object : URLShortenerIntents {
                override fun shortenURL(urlToShorten: String): Job {
                    TODO("Not yet implemented")
                }

                override fun getShortenedURLHistory(): Job {
                    TODO("Not yet implemented")
                }
            },
        )
    }
}
