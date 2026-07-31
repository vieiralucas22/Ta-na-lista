package com.example.tanalista.model.repository.local.implementation

import com.example.tanalista.model.database.dao.ProductCategoryDao
import com.example.tanalista.model.database.model.ProductCategoryEntity
import com.example.tanalista.model.repository.local.interfaces.ProductCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductCategoryRepositoryImpl @Inject constructor(
    private val productCategoryDao: ProductCategoryDao
): ProductCategoryRepository {

    override fun getAllCategories(): Flow<List<ProductCategoryEntity>> =
        productCategoryDao.getAllProductCategories()
}