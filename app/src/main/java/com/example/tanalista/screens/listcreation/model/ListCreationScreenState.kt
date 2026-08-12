package com.example.tanalista.screens.listcreation.model

import com.example.tanalista.R
import com.example.tanalista.dsm.ButtonState
import com.example.tanalista.dsm.colorpicker.model.ColorPickerState
import com.example.tanalista.dsm.colorpicker.model.ColorState
import com.example.tanalista.dsm.footer.model.FooterState
import com.example.tanalista.dsm.iconpicker.model.IconPickerState
import com.example.tanalista.dsm.iconpicker.model.IconState
import com.example.tanalista.dsm.textfield.model.TextFieldState

data class ListCreationScreenState(
    val colorPickerState: ColorPickerState,
    val iconPickerState: IconPickerState,
    val footerState: FooterState,
    val listNameTextFieldState: TextFieldState,
    val listDescriptionTextFieldState: TextFieldState,
) {
    companion object {
        val DEFAULT_STATE = ListCreationScreenState(
            colorPickerState = ColorPickerState(
                colors = listOf(
                    ColorState(R.color.light_blue, isSelected = false),
                ),
                sectionTitle = "Color",
            ),
            iconPickerState = IconPickerState(
                icons = listOf(
                    IconState(R.drawable.ic_shopping_cart_fill, isSelected = false),
                ),
                sectionTitle = "Icon",
            ),
            footerState = FooterState(
                ButtonState(R.string.cancel),
                ButtonState(R.string.create),
            ),
            listNameTextFieldState = TextFieldState(
                value = "",
                label = R.string.name,
                isError = false
            ),
            listDescriptionTextFieldState = TextFieldState(
                value = "",
                label = R.string.description,
                isError = false
            )
        )
    }
}
