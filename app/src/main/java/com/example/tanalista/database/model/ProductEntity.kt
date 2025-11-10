package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity (tableName = "Product")
data class ProductEntity(

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

)  : BaseEntity()
