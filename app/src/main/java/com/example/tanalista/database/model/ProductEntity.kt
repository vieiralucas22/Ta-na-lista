package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tanalista.enums.ProductCategory

@Entity (tableName = "Product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo
    val name : String,
    @ColumnInfo
    val price : Double,
    @ColumnInfo
    val quantity : Int,
    @ColumnInfo
    val isInCart : Boolean,
    @ColumnInfo
    val category: String
)
