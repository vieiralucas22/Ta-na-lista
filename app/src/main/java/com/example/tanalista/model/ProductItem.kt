package com.example.tanalista.model

import com.example.tanalista.enums.ProductCategory

data class ProductItem(
    val name : String,
    val price : Double,
    val quantity : Int,
    val isInCart : Boolean,
    val category: ProductCategory
)
