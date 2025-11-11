package com.example.tanalista.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tanalista.database.model.dto.ListItemDTO
import com.example.tanalista.database.model.ProductListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(productListEntity: ProductListEntity)

    @Query("SELECT p.name, pl.quantity, pl.productPrice, p.category, pl.isInCart " +
            "FROM Product p JOIN ProductList pl ON p.id = pl.productId " +
            "WHERE pl.listId = :listId")
    fun getProductsInList(listId: Int): Flow<List<ListItemDTO>>
}