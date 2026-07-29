package com.example.tanalista.di.modules.concurrency

import com.example.tanalista.concurrency.CoroutineDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {

    @Provides
    fun providesCoroutineDispatcherProvider() : CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider()
    }

}