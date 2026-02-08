package com.example.tanalista.repository.local.interfaces

import com.example.tanalista.model.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

interface IProductRepository {

    fun getAllProducts() : Flow<List<ProductEntity>>

}