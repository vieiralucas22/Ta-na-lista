package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductCategory")
data class ProductCategoryEntity (
    @PrimaryKey (autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    var categoryName : String = ""
)