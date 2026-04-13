package com.example.tanalista.screens.cart.model

import com.example.tanalista.R

data class HeaderToggleButtonState(
    val iconResourceId: Int = R.drawable.ic_cart,
    val isChecked: Boolean = false,
    val buttonState: ButtonState = ButtonState(),
)
