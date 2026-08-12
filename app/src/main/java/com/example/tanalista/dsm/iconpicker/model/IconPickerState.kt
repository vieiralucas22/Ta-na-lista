package com.example.tanalista.dsm.iconpicker.model


data class IconPickerState(
    val icons: List<IconState>,
    val sectionTitle: String,
) {
    fun updateSelectedIcon(icon: IconState): IconPickerState {
        val updatedIcons = icons.map { it.copy(isSelected = it.iconId == icon.iconId) }
        return copy(icons = updatedIcons)
    }
}
