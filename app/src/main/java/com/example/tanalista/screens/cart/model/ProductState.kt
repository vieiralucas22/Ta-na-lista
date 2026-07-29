package com.example.tanalista.screens.cart.model

import com.example.tanalista.enums.ProductCategory

data class ProductState(
    val name : String = "",
    val value: Double = 0.0,
    val quantity: Int = 0,
    val category : ProductCategory = ProductCategory.UNDEFINED,
    val isInCart: Boolean = false
)
