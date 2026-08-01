package com.example.tanalista.screens.cart.model

import com.example.tanalista.R
import com.example.tanalista.model.database.model.dto.ListItemDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class CartScreenState(
    val totalCartValue: Double,
    var cartToggle: HeaderToggleButtonState,
    var listToggle: HeaderToggleButtonState,
    val allProductsInList : Flow<List<ListItemDTO>>
) {
    companion object {
        val DEFAULT_STATE = CartScreenState(
            totalCartValue = 0.0,
            cartToggle = HeaderToggleButtonState(
                R.drawable.ic_cart,
                false,
                ButtonState(R.string.cart)
            ),
            listToggle = HeaderToggleButtonState(
                R.drawable.ic_list,
                true,
                ButtonState(R.string.list)
            ),
            allProductsInList = flowOf(emptyList())
        )
    }
}
