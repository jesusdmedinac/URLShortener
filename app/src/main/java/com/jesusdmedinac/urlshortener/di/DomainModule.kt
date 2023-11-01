package com.jesusdmedinac.urlshortener.di

import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused") // Used by Hilt
interface DomainModule {
    @Binds
    @Singleton
    fun bindURLShortenerRepository(
        urlShortenerRepositoryImpl: URLShortenerRepositoryImpl,
    ): URLShortenerRepository
}
