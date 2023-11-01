package com.jesusdmedinac.urlshortener.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @Provides
    @Named("io-dispatcher")
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcher =
        Dispatchers.IO
}
