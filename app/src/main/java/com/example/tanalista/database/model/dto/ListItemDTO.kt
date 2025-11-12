package com.example.tanalista.database.model.dto

data class ListItemDTO(
    val listId: Long = 0L,
    val productId: Long = 0L,
    val name : String,
    val quantity: Int,
    val productPrice: Double,
    val category: String,
    val isInCart: Boolean
)
