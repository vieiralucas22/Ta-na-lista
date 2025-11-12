package com.example.tanalista.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.database.model.ProductListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productListEntity: ProductListEntity)

    @Query("SELECT pl.listId, pl.productId, p.name, pl.quantity, pl.productPrice, p.category, pl.isInCart " +
            "FROM Product p JOIN ProductList pl ON p.id = pl.productId " +
            "WHERE pl.listId = :id")
    fun getProductsInList(id: Long): Flow<List<ListItemDTO>>

    @Query("SELECT * FROM ProductList WHERE listId=:listId AND productId=:productId")
    suspend fun getProductInListByIds(listId: Long, productId: Long): ProductListEntity

    @Delete
    suspend fun deleteProductList(productListEntity: ProductListEntity)
}