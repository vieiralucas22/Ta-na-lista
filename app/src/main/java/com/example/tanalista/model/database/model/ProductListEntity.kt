package com.example.tanalista.model.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.tanalista.constants.database.DatabaseConstants
import com.example.tanalista.enums.ProductCategory

@Entity(
    tableName = DatabaseConstants.PRODUCT_LIST_TABLE_NAME,
    primaryKeys = [DatabaseConstants.LIST_PRIMARY_KEY_NAME, DatabaseConstants.PRODUCT_PRIMARY_KEY_NAME],
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = [DatabaseConstants.ID],
            childColumns = [DatabaseConstants.LIST_PRIMARY_KEY_NAME],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = [DatabaseConstants.ID],
            childColumns = [DatabaseConstants.PRODUCT_PRIMARY_KEY_NAME],
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
