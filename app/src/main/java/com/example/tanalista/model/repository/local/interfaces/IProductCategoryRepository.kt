package com.example.tanalista.repository.local.interfaces

import com.example.tanalista.model.database.model.ProductCategoryEntity
import kotlinx.coroutines.flow.Flow

interface IProductCategoryRepository {

    fun getAllCategories() : Flow<List<ProductCategoryEntity>>

}