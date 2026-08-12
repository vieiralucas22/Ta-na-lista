package com.example.tanalista.dsm.colorpicker.model

import androidx.annotation.ColorRes

data class ColorState(
    @ColorRes val colorId: Int,
    val isSelected: Boolean
)
