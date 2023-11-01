package com.jesusdmedinac.urlshortener.di

import com.jesusdmedinac.urlshortener.domain.usecase.GetShortenedURLHistoryUseCase
import com.jesusdmedinac.urlshortener.domain.usecase.GetShortenedURLHistoryUseCaseImpl
import com.jesusdmedinac.urlshortener.domain.usecase.ShortenURLUseCase
import com.jesusdmedinac.urlshortener.domain.usecase.ShortenURLUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
@Suppress("unused") // Used by Hilt
interface PresentationModule {
    @Binds
    fun bindShortenUrlUseCase(
        shortenURLUseCaseImpl: ShortenURLUseCaseImpl,
    ): ShortenURLUseCase

    @Binds
    fun bindGetShortenedUrlUseCase(
        getShortenedURLHistoryUseCaseImpl: GetShortenedURLHistoryUseCaseImpl,
    ): GetShortenedURLHistoryUseCase
}
