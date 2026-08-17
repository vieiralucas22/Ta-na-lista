package com.example.tanalista.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tanalista.constants.database.DatabaseConstants.LIST_TABLE_NAME
import com.example.tanalista.model.database.model.ListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(listEntity: ListEntity)

    @Update
    fun updateList(listEntity: ListEntity)

    @Query("SELECT * FROM $LIST_TABLE_NAME")
    fun getAllLists(): Flow<List<ListEntity>>

    @Query("SELECT * FROM $LIST_TABLE_NAME WHERE id = :listId")
    fun getListById(listId: Long): ListEntity?

    @Query("DELETE FROM $LIST_TABLE_NAME WHERE id = :listId")
    fun deleteListFromDatabase(listId: Long)

}