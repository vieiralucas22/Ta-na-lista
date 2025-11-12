package com.example.tanalista.database.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ProductList", primaryKeys = ["listId", "productId"], foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class ProductListEntity(
    val listId: Long = 0,
    val productId: Long = 0,
    var quantity: Int = 0,
    var productPrice: Double = 0.0,
    var isInCart: Boolean = false
)
