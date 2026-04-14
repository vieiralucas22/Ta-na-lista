package com.example.tanalista.di.modules.concurrency

import com.example.tanalista.concurrency.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    fun providesDispatcherProvider() : DispatcherProvider {
        return DispatcherProvider()
    }

}