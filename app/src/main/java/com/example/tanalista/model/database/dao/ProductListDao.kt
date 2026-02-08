package com.example.tanalista.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.model.database.model.ProductListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productListEntity: ProductListEntity)

    @Query("SELECT * FROM ProductList WHERE listId = :id AND isInCart = :isInCart")
    fun getProductsInList(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>>

    @Query("SELECT pl.listId, pl.productId, pl.name, pl.quantity, pl.productPrice, pl.category, pl.isInCart " +
            "FROM Product p JOIN ProductList pl ON p.id = pl.productId " +
            "WHERE pl.listId = :id AND pl.isInCart = :isInCart " +
            "ORDER BY pl.name ASC")
    fun getProductsInListOrderByAlphabetical(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>>

    @Query("SELECT pl.listId, pl.productId, pl.name, pl.quantity, pl.productPrice, pl.category, pl.isInCart " +
            "FROM Product p JOIN ProductList pl ON p.id = pl.productId " +
            "WHERE pl.listId = :id AND pl.isInCart = :isInCart " +
            "ORDER BY pl.category ASC")
    fun getProductsInListOrderByCategory(id: Long, isInCart: Boolean): Flow<List<ListItemDTO>>

    @Query("SELECT * FROM ProductList WHERE listId=:listId AND productId=:productId")
    suspend fun getProductInListByIds(listId: Long, productId: Long): ProductListEntity

    @Delete
    suspend fun deleteProductList(productListEntity: ProductListEntity)

}