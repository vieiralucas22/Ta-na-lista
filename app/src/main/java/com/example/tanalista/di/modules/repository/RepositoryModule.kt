package com.example.tanalista.di.modules.repository

import com.example.tanalista.model.repository.local.implementation.ListRepositoryImpl
import com.example.tanalista.model.repository.local.implementation.ProductCategoryRepositoryImpl
import com.example.tanalista.model.repository.local.implementation.ProductListRepositoryImpl
import com.example.tanalista.model.repository.local.implementation.ProductRepositoryImpl
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import com.example.tanalista.model.repository.local.interfaces.ProductCategoryRepository
import com.example.tanalista.model.repository.local.interfaces.ProductListRepository
import com.example.tanalista.repository.local.interfaces.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindListRepository(impl : ListRepositoryImpl) : ListRepository

    @Binds
    fun bindProductCategoryRepository(impl : ProductCategoryRepositoryImpl) : ProductCategoryRepository

    @Binds
    fun bindProductListRepository(impl : ProductListRepositoryImpl) : ProductListRepository

    @Binds
    fun bindProductRepository(impl : ProductRepositoryImpl) : ProductRepository
}