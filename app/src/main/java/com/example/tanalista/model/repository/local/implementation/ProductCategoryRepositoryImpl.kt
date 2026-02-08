package com.example.tanalista.repository.local.implementation

import com.example.tanalista.model.database.dao.ProductCategoryDao
import com.example.tanalista.model.database.model.ProductCategoryEntity
import com.example.tanalista.repository.local.interfaces.IProductCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductCategoryRepositoryImpl @Inject constructor(
    val productCategoryDao: ProductCategoryDao) : IProductCategoryRepository {

    override fun getAllCategories(): Flow<List<ProductCategoryEntity>> {
        return productCategoryDao.getAllProductCategories()
    }
}