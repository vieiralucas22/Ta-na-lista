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
    suspend fun insertProduct(product : ProductEntity)

    @Query("select * from Product")
    fun getAllProducts() : Flow<List<ProductEntity>>

    @Query("UPDATE Product SET isInCart = 1 WHERE id = :id")
    suspend fun addProductToCart(id :Int)

    @Query("UPDATE Product SET isInCart = 0 WHERE id = :id")
    suspend fun removeProductFromCart(id :Int)

}