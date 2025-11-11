package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.ApplicationDatabase
import com.example.tanalista.database.model.ProductCategoryEntity
import kotlinx.coroutines.flow.Flow

class ProductCategoryRepository (context : Context) {

    val productCategoryDao = ApplicationDatabase.getDatabase(context).ProductCategoryDao()

    fun getAllCategories() : Flow<List<ProductCategoryEntity>>
    {
        return productCategoryDao.getAllProductCategories()
    }
}