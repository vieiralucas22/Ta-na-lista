package com.example.tanalista.model.repository.local.interfaces

import com.example.tanalista.model.database.model.ProductCategoryEntity
import kotlinx.coroutines.flow.Flow

interface ProductCategoryRepository {

    fun getAllCategories() : Flow<List<ProductCategoryEntity>>

}