package com.example.tanalista.model.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.tanalista.constants.database.DatabaseConstants

@Entity (tableName = DatabaseConstants.ProductTableName)
data class ProductEntity(

    @ColumnInfo
    var name : String = "",
    @ColumnInfo
    var category: String = ""

)  : BaseEntity()
