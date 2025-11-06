package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.TaNaListaDatabase
import com.example.tanalista.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository (context: Context) {

    val productDao = TaNaListaDatabase.getDatabase(context).ProductDao()

    fun getAllProducts() : Flow<List<ProductEntity>>{
        return productDao.getAllProducts()
    }

    suspend fun addProductToCart(product: ProductEntity)
    {
        productDao.addProductToCart(product.id)
    }

    suspend fun removeProductFromCart(product: ProductEntity) {
        productDao.removeProductFromCart(product.id)
    }
}