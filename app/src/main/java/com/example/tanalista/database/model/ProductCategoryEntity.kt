package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.tanalista.constants.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.ProductCategoryTableName)
data class ProductCategoryEntity (

    @ColumnInfo
    var categoryName : String = ""
)  : BaseEntity()