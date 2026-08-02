package com.example.tanalista.screens.listcreation.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ListCreationScreenState(
    val listName: String,
    val description: String,
    val colorId: Int,
    val icon: ImageVector,
    val onCancelAction: ()-> Unit,
    val onCreateAction: ()-> Unit,
)
