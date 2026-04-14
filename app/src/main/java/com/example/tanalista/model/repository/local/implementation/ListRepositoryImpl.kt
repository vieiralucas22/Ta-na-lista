package com.example.tanalista.repository.local.implementation

import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.repository.local.interfaces.IListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(val listDAO: ListDao) : IListRepository {

    override suspend fun insertList(name: String) = withContext(Dispatchers.IO)
    {
        val newList = ListEntity("Mercado")

        listDAO.insertList(newList)
    }

    override fun getAllListsFromDatabase(): Flow<List<ListEntity>> {
        return listDAO.getAllLists()
    }

}