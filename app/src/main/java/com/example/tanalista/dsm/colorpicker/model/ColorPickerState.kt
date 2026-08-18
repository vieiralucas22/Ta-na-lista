package com.example.tanalista.dsm.colorpicker.model

import com.example.tanalista.R
import com.example.tanalista.dsm.formsection.model.FormSectionState

data class ColorPickerState(
    val colors: List<ColorState>,
    override val sectionTitle: Int,
) : FormSectionState {
    fun updateSelectedColor(color: ColorState): ColorPickerState {
        val updatedColors = colors.map { it.copy(isSelected = it.colorId == color.colorId) }
        return copy(colors = updatedColors)
    }

    companion object {

        val DEFAULT_COLORS = listOf(
            R.color.light_blue,
            R.color.light_green,
            R.color.light_yellow,
            R.color.light_red,
            R.color.light_pink,
            R.color.light_aqua_green,
        )
    }
}
