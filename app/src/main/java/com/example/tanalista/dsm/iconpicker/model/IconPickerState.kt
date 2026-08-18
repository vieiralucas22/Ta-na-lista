package com.example.tanalista.dsm.iconpicker.model

import com.example.tanalista.R
import com.example.tanalista.dsm.formsection.model.FormSectionState


data class IconPickerState(
    val icons: List<IconState>,
    val iconSelected: IconState,
    override val sectionTitle: Int,
) : FormSectionState {

    companion object {
        val DEFAULT_ICONS = listOf(
            R.drawable.ic_shopping_cart_fill,
            R.drawable.ic_home_fill,
            R.drawable.ic_heart_fill,
            R.drawable.ic_book_open_cover_fill,
            R.drawable.ic_hamburger_fill,
            R.drawable.ic_medicine_fill,
            R.drawable.ic_shopping_bag_fill,
            R.drawable.ic_dog_fill,
        )
    }
}
