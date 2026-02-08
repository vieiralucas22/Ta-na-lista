package com.example.tanalista.di.modules.repository

import com.example.tanalista.repository.local.implementation.ListRepositoryImpl
import com.example.tanalista.repository.local.implementation.ProductCategoryRepositoryImpl
import com.example.tanalista.repository.local.implementation.ProductListRepositoryImpl
import com.example.tanalista.repository.local.implementation.ProductRepositoryImpl
import com.example.tanalista.repository.local.interfaces.IListRepository
import com.example.tanalista.repository.local.interfaces.IProductCategoryRepository
import com.example.tanalista.repository.local.interfaces.IProductListRepository
import com.example.tanalista.repository.local.interfaces.IProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsListRepository(impl : ListRepositoryImpl) : IListRepository

    @Binds
    fun bindsProductCategoryRepository(impl : ProductCategoryRepositoryImpl) : IProductCategoryRepository

    @Binds
    fun bindsProductListRepository(impl : ProductListRepositoryImpl) : IProductListRepository

    @Binds
    fun bindsProductRepository(impl : ProductRepositoryImpl) : IProductRepository
}