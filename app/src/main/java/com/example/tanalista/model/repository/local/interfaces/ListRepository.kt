package com.example.tanalista.model.repository.local.interfaces

import com.example.tanalista.model.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun createList(name: String, description: String, colorId: Int, iconId: Int)

    fun getAllListsFromDatabase() : Flow<List<ListEntity>>

    suspend fun deleteList(listId: Long)

}