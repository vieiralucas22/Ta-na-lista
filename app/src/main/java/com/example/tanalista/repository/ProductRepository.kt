package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.TaNaListaDatabase
import com.example.tanalista.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository (context: Context) {

    val productDao = TaNaListaDatabase.getDatabase(context).ProductDao()

    fun getAllProducts() : Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getAllListProducts() : Flow<List<ProductEntity>>
    {
        return productDao.getAllProducts();
    }

    suspend fun removeProductFromCart(product: ProductEntity) {
        productDao.removeProductFromCart(product.id)
    }
}