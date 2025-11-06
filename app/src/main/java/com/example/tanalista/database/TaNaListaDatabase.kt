package com.example.tanalista.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tanalista.database.dao.ProductCategoryDao
import com.example.tanalista.database.dao.ProductDao
import com.example.tanalista.database.model.ProductCategoryEntity
import com.example.tanalista.database.model.ProductEntity
import com.example.tanalista.enums.ProductCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ProductEntity::class, ProductCategoryEntity::class], version = 1)
abstract class TaNaListaDatabase : RoomDatabase() {

    abstract fun ProductDao() : ProductDao
    abstract fun ProductCategoryDao() : ProductCategoryDao

    companion object {
        val DB_NAME = "taNaListaDB"
        lateinit var INSTANCE: TaNaListaDatabase

        fun getDatabase(context: Context): TaNaListaDatabase {

            if (!::INSTANCE.isInitialized) {
                synchronized(TaNaListaDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(context, TaNaListaDatabase::class.java, DB_NAME)
                        .addCallback(DatabaseCallback(context))
                        .build()
                }
            }

            return INSTANCE
        }
    }

    private class DatabaseCallback(
        private val context: Context
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                populateProductCategoryDatabase(context)
                populateProductDatabase(context)
            }
        }

        private suspend fun populateProductDatabase(context: Context) {

            val productDao = getDatabase(context).ProductDao()

            val jsonString = context.assets.open("market_list.json")
                .bufferedReader()
                .use { it.readText() }

            val gson = Gson()
            val itemType = object : TypeToken<List<ProductEntity>>() {}.type
            val products: List<ProductEntity> = gson.fromJson(jsonString, itemType)

            for (product in products)
            {
                productDao.insertProduct(product)
            }
        }

        private suspend fun populateProductCategoryDatabase(context: Context) {
            val categoryDao = getDatabase(context).ProductCategoryDao()

            for (category in ProductCategory.entries)
            {
                val entity = ProductCategoryEntity()
                entity.categoryName = category.name
                categoryDao.insertProductCategory(entity)
            }
        }
    }
}