package com.jesusdmedinac.urlshortener.di

import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepository
import com.jesusdmedinac.urlshortener.domain.repository.URLShortenerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {
    @Binds
    fun bindURLShortenerRepository(
        urlShortenerRepositoryImpl: URLShortenerRepositoryImpl,
    ): URLShortenerRepository
}
