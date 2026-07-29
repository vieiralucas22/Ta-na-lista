package com.example.tanalista.model.repository.local.interfaces

import com.example.tanalista.model.database.model.dto.ListItemDTO
import kotlinx.coroutines.flow.Flow

interface ProductListRepository {
    suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?)
    suspend fun addProductToCart(item: ListItemDTO)

    fun getAllListProducts(id: Long, isInCart: Boolean = false): Flow<List<ListItemDTO>>

    suspend fun removeProductFromCart(item: ListItemDTO)
    suspend fun deleteProductFromList(item: ListItemDTO)
}