package com.example.tanalista.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tanalista.model.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listEntity: ListEntity)

    @Query("select * from List where id=:id")
    suspend fun getListById(id: Int) : ListEntity

    @Query("select * from List")
    fun getAllLists() : Flow<List<ListEntity>>

}