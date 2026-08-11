package com.example.tanalista.screens.util.model.colorpicker

import androidx.annotation.ColorRes

data class ColorState(
    @ColorRes val colorId: Int,
    val isSelected: Boolean
)
