package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.ApplicationDatabase
import com.example.tanalista.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository (context: Context) {

    val productDao = ApplicationDatabase.getDatabase(context).ProductDao()

    fun getAllProducts() : Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

}