package com.example.tanalista.di.modules.screens

import com.example.tanalista.screens.home.converter.ListComponentConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ConverterModule {

    @Provides
    fun providesListComponentConverter() : ListComponentConverter = ListComponentConverter()
}