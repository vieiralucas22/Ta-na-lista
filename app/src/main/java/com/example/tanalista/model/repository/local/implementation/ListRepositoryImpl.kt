package com.example.tanalista.repository.local.implementation

import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.repository.local.interfaces.IListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor (val listDAO : ListDao) : IListRepository {

    override suspend fun insertList(name: String) {
        val newList = ListEntity("Mercado")

        listDAO.insertList(newList)
    }

    override fun getAllListsFromDatabase() : Flow<List<ListEntity>>
    {
        return listDAO.getAllLists()
    }

}