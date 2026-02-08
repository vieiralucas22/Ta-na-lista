package com.example.tanalista.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.dao.ProductCategoryDao
import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.dao.ProductListDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.model.database.model.ProductCategoryEntity
import com.example.tanalista.model.database.model.ProductEntity
import com.example.tanalista.model.database.model.ProductListEntity

@Database(
    entities = [
        ProductEntity::class,
        ProductCategoryEntity::class,
        ListEntity::class,
        ProductListEntity::class],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getProductCategoryDao(): ProductCategoryDao
    abstract fun getListDao(): ListDao
    abstract fun getProductListDao(): ProductListDao

}