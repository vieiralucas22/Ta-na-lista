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
    val quantity: Int = 0,
    val productPrice: Double = 0.0,
    val isInCart: Boolean = false
)
