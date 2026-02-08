package com.example.tanalista.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.tanalista.model.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert (onConflict = REPLACE)
    suspend fun insertProduct(product : ProductEntity): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("select * from Product")
    fun getAllProducts() : Flow<List<ProductEntity>>

    @Query("select * from Product where id = :id")
    fun getProductById(id : Int) : ProductEntity

    @Query("SELECT COUNT(*) FROM Product")
    suspend fun getTotalProducts(): Int

}