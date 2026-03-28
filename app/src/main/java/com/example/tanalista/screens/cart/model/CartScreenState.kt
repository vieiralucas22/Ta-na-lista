package com.example.tanalista.screens.cart.model

import com.example.tanalista.model.database.model.dto.ListItemDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class CartScreenState(
    val totalCartValue: Double = 0.0,
    var cartToggle: HeaderToggleButtonState = HeaderToggleButtonState(),
    var listToggle: HeaderToggleButtonState = HeaderToggleButtonState(),
    val allProductsInList : Flow<List<ListItemDTO>> = flowOf(emptyList())
)
