package com.example.tanalista.repository.local.interfaces

import com.example.tanalista.model.database.model.dto.ListItemDTO
import kotlinx.coroutines.flow.Flow

interface IProductListRepository {
    suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?)
    suspend fun addProductToCart(listItem: ListItemDTO)

    fun getAllListProducts(id: Long, isInCart: Boolean = false): Flow<List<ListItemDTO>>

    suspend fun removeProductFromCart(listItem: ListItemDTO)
    suspend fun deleteProductFromList(listItem: ListItemDTO)
}