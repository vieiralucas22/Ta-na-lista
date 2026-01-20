package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.ApplicationDatabase
import com.example.tanalista.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

class ListRepository (context : Context){

    val listDao = ApplicationDatabase.getDatabase(context).ListDao()

    suspend fun createAList(name: String) {
        val newList = ListEntity("Mercado")

        listDao.insertList(newList)
    }

    fun getAllListsFromDatabase() : Flow<List<ListEntity>>
    {
        return listDao.getAllLists()
    }

}