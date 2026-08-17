package com.example.tanalista.model.repository.local.implementation

import com.example.tanalista.concurrency.CoroutineDispatcherProvider
import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val listDAO: ListDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ListRepository {

    override suspend fun createList(name: String, description: String, colorId: Int, iconId: Int) =
        withContext(coroutineDispatcherProvider.io) {
            val listEntity = ListEntity(name, description, colorId, iconId)

            listDAO.insertList(listEntity)
        }

    override suspend fun updateList(
        listId: Long,
        name: String,
        description: String,
        colorId: Int,
        iconId: Int
    ) = withContext(coroutineDispatcherProvider.io) {
        val listEntity = ListEntity(name, description, colorId, iconId).apply { id = listId }

        listDAO.updateList(listEntity)
    }

    override suspend fun getListById(listId: Long): ListEntity? =
        withContext(coroutineDispatcherProvider.io) {
            listDAO.getListById(listId)
        }

    override fun getAllListsFromDatabase(): Flow<List<ListEntity>> = listDAO.getAllLists()

    override suspend fun deleteList(listId: Long) = withContext(coroutineDispatcherProvider.io) {
        listDAO.deleteListFromDatabase(listId)
    }
}