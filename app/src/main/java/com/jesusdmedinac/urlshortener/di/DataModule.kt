package com.jesusdmedinac.urlshortener.di

import com.jesusdmedinac.urlshortener.data.local.URLShortenerInMemoryDataSource
import com.jesusdmedinac.urlshortener.data.local.URLShortenerLocalDataSource
import com.jesusdmedinac.urlshortener.data.remote.URLShortenerKtorDataSource
import com.jesusdmedinac.urlshortener.data.remote.URLShortenerRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused") // Used by Hilt
interface DataModule {
    @Binds
    fun bindURLShortenerLocalDataSource(
        urlShortenerInMemoryDataSource: URLShortenerInMemoryDataSource,
    ): URLShortenerLocalDataSource

    @Binds
    fun bindURLShortenerRemoteDataSource(
        urlShortenerKtorDataSource: URLShortenerKtorDataSource,
    ): URLShortenerRemoteDataSource
}
