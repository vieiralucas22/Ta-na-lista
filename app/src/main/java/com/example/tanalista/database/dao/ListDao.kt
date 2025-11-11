package com.example.tanalista.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tanalista.database.model.ListEntity

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(listEntity: ListEntity)

    @Query("select * from List where id=:id")
    suspend fun getListById(id: Int) : ListEntity

}