package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "ProductCategory")
data class ProductCategoryEntity (

    @ColumnInfo
    var categoryName : String = ""
)  : BaseEntity()