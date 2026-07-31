package com.example.tanalista.model.repository.local.implementation

import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.repository.local.interfaces.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDAO: ProductDao
) : ProductRepository {

    /* Override Methods */

    override fun getAllProducts(): Flow<List<ProductEntity>> = productDAO.getAllProducts()

}