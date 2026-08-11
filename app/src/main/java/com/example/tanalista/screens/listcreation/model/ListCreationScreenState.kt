
package com.example.tanalista.screens.listcreation.model

import com.example.tanalista.R
import com.example.tanalista.screens.util.model.colorpicker.ColorPickerState
import com.example.tanalista.screens.util.model.colorpicker.ColorState
import com.example.tanalista.screens.util.model.iconpicker.IconPickerState
import com.example.tanalista.screens.util.model.iconpicker.IconState

data class ListCreationScreenState(
    val listName: String,
    val description: String,
    val colorPickerState: ColorPickerState,
    val iconPickerState: IconPickerState
) {
    companion object {
        val DEFAULT_STATE = ListCreationScreenState(
            listName = "",
            description = "",
            colorPickerState = ColorPickerState(
                colors = listOf(
                    ColorState(R.color.light_blue, isSelected = false),
                ),
                sectionTitle = "Color",
            ),
            iconPickerState = IconPickerState (
                icons = listOf(
                    IconState (R.drawable.ic_shopping_cart_fill, isSelected = false),
                ),
                sectionTitle = "Icon",
            )
        )
    }
}
