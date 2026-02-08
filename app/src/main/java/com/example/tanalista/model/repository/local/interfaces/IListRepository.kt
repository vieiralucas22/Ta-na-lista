package com.example.tanalista.repository.local.interfaces

import com.example.tanalista.model.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

interface IListRepository {

    suspend fun insertList(name: String)

    fun getAllListsFromDatabase() : Flow<List<ListEntity>>

}