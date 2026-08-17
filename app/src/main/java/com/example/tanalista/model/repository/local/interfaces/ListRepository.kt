package com.example.tanalista.model.repository.local.interfaces

import com.example.tanalista.model.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun createList(name: String, description: String, colorId: Int, iconId: Int)

    suspend fun updateList(listId: Long, name: String, description: String, colorId: Int, iconId: Int)

    suspend fun getListById(listId: Long): ListEntity?

    fun getAllListsFromDatabase() : Flow<List<ListEntity>>

    suspend fun deleteList(listId: Long)

}