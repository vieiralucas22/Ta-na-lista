package com.example.tanalista.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.tanalista.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert (onConflict = REPLACE)
    suspend fun insertProduct(product : ProductEntity): Long

    @Query("select * from Product")
    fun getAllProducts() : Flow<List<ProductEntity>>

    @Query("select * from Product where id = :id")
    fun getProductById(id : Int) : ProductEntity

}