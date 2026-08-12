package com.example.tanalista.screens.cart.model

import com.example.tanalista.dsm.ButtonState

data class HeaderToggleButtonState(
    val iconResourceId: Int,
    val isChecked: Boolean,
    val buttonState: ButtonState,
)
