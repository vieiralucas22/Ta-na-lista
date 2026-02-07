package com.example.tanalista.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.tanalista.constants.database.DatabaseConstants
import com.example.tanalista.enums.ProductCategory

@Entity(
    tableName = DatabaseConstants.ProducListTableName,
    primaryKeys = [DatabaseConstants.ListPrimaryKeyName, DatabaseConstants.ProducListTableName],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = [DatabaseConstants.Id],
            childColumns = [DatabaseConstants.ListPrimaryKeyName],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = [DatabaseConstants.Id],
            childColumns = [DatabaseConstants.ProductPrimaryKeyName],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class ProductListEntity(
    val listId: Long = 0,
    val productId: Long = 0,
    var name: String = "",
    var quantity: Int = 0,
    var productPrice: Double = 0.0,
    var isInCart: Boolean = false,
    var category: String = ProductCategory.Undefined.toString()
)
