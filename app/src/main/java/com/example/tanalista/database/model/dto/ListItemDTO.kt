package com.example.tanalista.database.model.dto

data class ListItemDTO(
    val name : String,
    val quantity: Int,
    val productPrice: Double,
    val category: String,
    val isInCart: Boolean
)
