package com.jesusdmedinac.urlshortener.presentation.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerSideEffect
import com.jesusdmedinac.urlshortener.presentation.viewmodel.URLShortenerViewModel

@Composable
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun URLShortenerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val uriHandler = LocalUriHandler.current

            val urlShortenerViewModel: URLShortenerViewModel = hiltViewModel()
            val urlShortenerState by urlShortenerViewModel.container.stateFlow.collectAsState()
            val urlShortenerSideEffect by urlShortenerViewModel.container.sideEffectFlow.collectAsState(
                initial = URLShortenerSideEffect.Idle,
            )
            LaunchedEffect(urlShortenerSideEffect) {
                when (val sideEffect = urlShortenerSideEffect) {
                    URLShortenerSideEffect.Idle -> Unit
                    is URLShortenerSideEffect.OnShortenedURLClicked -> {
                        uriHandler.openUri(sideEffect.shortenedURL.links.self)
                    }
                }
            }
            HomeScreen(
                urlShortenerState,
                urlShortenerViewModel,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("FunctionNaming") // Compose functions naming is upper camel case
fun URLShortenerAppPreview() {
    URLShortenerApp()
}
