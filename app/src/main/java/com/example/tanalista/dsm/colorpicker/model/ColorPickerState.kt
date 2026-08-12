package com.example.tanalista.dsm.colorpicker.model

data class ColorPickerState(
    val colors: List<ColorState>,
    val sectionTitle: String,
) {
    fun updateSelectedColor(color: ColorState): ColorPickerState {
        val updatedColors = colors.map { it.copy(isSelected = it.colorId == color.colorId) }
        return copy(colors = updatedColors)
    }
}
