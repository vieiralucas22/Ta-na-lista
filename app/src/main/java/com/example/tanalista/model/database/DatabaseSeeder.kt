package com.example.tanalista.model.database

import android.content.Context
import com.example.tanalista.enums.ProductCategory
import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.dao.ProductCategoryDao
import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.model.database.model.ProductCategoryEntity
import com.example.tanalista.model.database.model.ProductEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: ProductCategoryDao,
    private val listDao: ListDao,
    @ApplicationContext private val context: Context
) {

    suspend fun seedDatabase() {

        if (productDao.getTotalProducts() > 0) return

        populateProducts()
        populateCategories()
        populateLists()
    }

    private suspend fun populateProducts() {

        val jsonString = context.assets.open("market_list.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val itemType = object : TypeToken<List<ProductEntity>>() {}.type
        val products: List<ProductEntity> =
            gson.fromJson(jsonString, itemType)

        productDao.insertAll(products)
    }

    private suspend fun populateCategories() {

        val entities = ProductCategory.entries.map {
            ProductCategoryEntity(categoryName = it.name)
        }

        categoryDao.insertAll(entities)
    }

    private suspend fun populateLists() {
        listDao.insertList(ListEntity("Mercado"))
    }
}
