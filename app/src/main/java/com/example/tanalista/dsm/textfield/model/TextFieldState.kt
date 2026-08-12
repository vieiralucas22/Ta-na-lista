package com.example.tanalista.dsm.textfield.model

import androidx.annotation.StringRes

data class TextFieldState(
    val value: String,
    @StringRes val label: Int,
    val isError: Boolean,
)
