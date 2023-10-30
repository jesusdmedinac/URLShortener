package com.jesusdmedinac.urlshortener.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object CoroutineModule {
    @Provides
    @Named("io-dispatcher")
    fun provideCoroutineDispatcherProvider(): CoroutineDispatcher =
        Dispatchers.IO
}
