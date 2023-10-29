package com.jesusdmedinac.urlshortener.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class URLShortenerViewModel @Inject constructor() : ViewModel() {
    val url = "https://www.google.com"
}
