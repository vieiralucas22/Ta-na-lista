package com.example.tanalista.screens.home.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ListComponentState(
    val name: String,
    val description: String,
    val color: Color,
    val icon: @Composable () -> Unit
)
