package com.example.tanalista.repository.local.implementation

import com.example.tanalista.concurrency.CoroutineDispatcherProvider
import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    val listDAO: ListDao,
    val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ListRepository {

    override suspend fun insertList(name: String) = withContext(coroutineDispatcherProvider.io)
    {
        val newList = ListEntity("Mercado")

        listDAO.insertList(newList)
    }

    override fun getAllListsFromDatabase(): Flow<List<ListEntity>> {
        return listDAO.getAllLists()
    }

}