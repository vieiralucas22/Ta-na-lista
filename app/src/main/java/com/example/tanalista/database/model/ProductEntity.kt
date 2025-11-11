package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity (tableName = "Product")
data class ProductEntity(

    @ColumnInfo
    var name : String = "",
    @ColumnInfo
    var category: String = ""

)  : BaseEntity()
