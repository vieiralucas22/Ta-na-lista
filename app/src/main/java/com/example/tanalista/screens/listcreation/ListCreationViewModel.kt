package com.example.tanalista.screens.listcreation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.listcreation.model.ListCreationScreenState
import com.example.tanalista.screens.util.model.colorpicker.ColorPickerState
import com.example.tanalista.screens.util.model.colorpicker.ColorState
import com.example.tanalista.screens.util.model.iconpicker.IconPickerState
import com.example.tanalista.screens.util.model.iconpicker.IconState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    application: Application,
    private val listRepository: ListRepository
) : BaseViewModel(application) {

    private val _uiState = mutableStateOf(ListCreationScreenState.DEFAULT_STATE)
    val uiState: State<ListCreationScreenState> = _uiState

    fun initializeScreenData() {
        _uiState.value = ListCreationScreenState(
            listName = "",
            description = "",
            colorPickerState = ColorPickerState(
                colors = listOf(
                    ColorState(R.color.light_blue, isSelected = true),
                    ColorState(R.color.light_green, isSelected = false),
                    ColorState(R.color.light_yellow, isSelected = false),
                    ColorState(R.color.light_red, isSelected = false),
                    ColorState(R.color.light_pink, isSelected = false),
                    ColorState(R.color.light_aqua_green, isSelected = false),
                ),
                sectionTitle = "Color",
            ),
            iconPickerState = IconPickerState(
                icons = listOf(
                    IconState(R.drawable.ic_shopping_cart_fill, isSelected = true),
                    IconState(R.drawable.ic_home_fill, isSelected = false),
                    IconState(R.drawable.ic_heart_fill, isSelected = false),
                    IconState(R.drawable.ic_book_open_cover_fill, isSelected = false),
                    IconState(R.drawable.ic_hamburger_fill, isSelected = false),
                    IconState(R.drawable.ic_medicine_fill, isSelected = false),
                    IconState(R.drawable.ic_shopping_bag_fill, isSelected = false),
                    IconState(R.drawable.ic_dog_fill, isSelected = false),
                ),
                sectionTitle = "Icon",
            )
        )
    }

    fun updateListName(value: String) {
        _uiState.value = _uiState.value.copy(listName = value)
    }

    fun updateListDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun updateSelectedColor(color: ColorState) {
        val colorPickerState = _uiState.value.colorPickerState
        _uiState.value =
            _uiState.value.copy(colorPickerState = colorPickerState.updateSelectedColor(color))
    }

    fun updateSelectedIcon(icon: IconState) {
        val iconPickerState = _uiState.value.iconPickerState
        _uiState.value =
            _uiState.value.copy(iconPickerState = iconPickerState.updateSelectedIcon(icon))
    }

    fun createList() {
        val name = uiState.value.listName
        val description = uiState.value.description
        val colorId = uiState.value.colorPickerState.colors.first { it.isSelected }.colorId
        val iconId = uiState.value.iconPickerState.icons.first { it.isSelected }.iconId

        if (name.isEmpty() || description.isEmpty()) {
            return
        }
        viewModelScope.launch {
            listRepository.createList(name, description, colorId, iconId)
        }
    }

}