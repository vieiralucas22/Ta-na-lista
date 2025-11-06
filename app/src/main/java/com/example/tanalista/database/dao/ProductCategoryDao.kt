package com.example.tanalista.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.tanalista.database.model.ProductCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCategoryDao {

    @Insert (onConflict = REPLACE)
    suspend fun insertProductCategory(category : ProductCategoryEntity)

    @Query("select * from ProductCategory")
    fun getAllProductCategories() : Flow<List<ProductCategoryEntity>>

}