package com.example.tanalista.repository.local.interfaces

import com.example.tanalista.model.database.model.dto.ListItemDTO
import kotlinx.coroutines.flow.Flow

interface IProductListRepository {
    suspend fun addOrUpdateProductInList(listItemDTO: ListItemDTO?)
    suspend fun addProductInCart(listItem: ListItemDTO)

    fun getAllListProducts(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>>

    suspend fun deleteProductInCart(listItem: ListItemDTO)
    suspend fun deleteProductFromList(listItem: ListItemDTO)
}