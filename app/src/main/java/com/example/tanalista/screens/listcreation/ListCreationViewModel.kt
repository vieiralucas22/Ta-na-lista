package com.example.tanalista.screens.listcreation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.tanalista.R
import com.example.tanalista.dsm.ButtonState
import com.example.tanalista.model.repository.local.interfaces.ListRepository
import com.example.tanalista.screens.BaseViewModel
import com.example.tanalista.screens.listcreation.model.ListCreationScreenState
import com.example.tanalista.dsm.colorpicker.model.ColorPickerState
import com.example.tanalista.dsm.colorpicker.model.ColorState
import com.example.tanalista.dsm.footer.model.FooterState
import com.example.tanalista.dsm.iconpicker.model.IconPickerState
import com.example.tanalista.dsm.iconpicker.model.IconState
import com.example.tanalista.dsm.textfield.model.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    application: Application,
    private val listRepository: ListRepository
) : BaseViewModel(application) {

    var listId: Long = 0L

    private val _uiState = mutableStateOf(ListCreationScreenState.DEFAULT_STATE)
    val uiState: State<ListCreationScreenState> = _uiState

    private val iconOptions = listOf(
        R.drawable.ic_shopping_cart_fill,
        R.drawable.ic_home_fill,
        R.drawable.ic_heart_fill,
        R.drawable.ic_book_open_cover_fill,
        R.drawable.ic_hamburger_fill,
        R.drawable.ic_medicine_fill,
        R.drawable.ic_shopping_bag_fill,
        R.drawable.ic_dog_fill,
    )

    fun initializeScreenData() {
        viewModelScope.launch {

            val existingList = listRepository.getListById(listId)

            _uiState.value = ListCreationScreenState(
                colorPickerState = ColorPickerState(
                    colors = ColorPickerState.DEFAULT_COLORS.map { colorId ->
                        ColorState(
                            colorId = colorId,
                        )
                    },
                    colorSelected = if (existingList != null) ColorState(existingList.colorId) else ColorState(
                        ColorPickerState.DEFAULT_COLORS[0]
                    ),
                    sectionTitle = R.string.color,
                ),
                iconPickerState = IconPickerState(
                    icons = iconOptions.map { iconId ->
                        IconState(
                            iconId,
                            isSelected = iconId == (existingList?.iconId ?: iconOptions.first())
                        )
                    },
                    sectionTitle = "Icon",
                ),
                footerState = FooterState(
                    ButtonState(R.string.cancel),
                    ButtonState(if (existingList != null) R.string.update else R.string.create),
                ),
                listNameTextFieldState = TextFieldState(
                    value = existingList?.name ?: "",
                    label = R.string.name,
                    isError = false
                ),
                listDescriptionTextFieldState = TextFieldState(
                    value = existingList?.description ?: "",
                    label = R.string.description,
                    isError = false
                )
            )
        }
    }

    fun updateListName(value: String) {
        _uiState.value = _uiState.value.copy(
            listNameTextFieldState = _uiState.value.listNameTextFieldState.copy(value = value)
        )

        _uiState.value = _uiState.value.copy(
            listNameTextFieldState = _uiState.value.listNameTextFieldState.copy(isError = value.isBlank())
        )
    }

    fun updateListDescription(value: String) {
        _uiState.value = _uiState.value.copy(
            listDescriptionTextFieldState = _uiState.value.listDescriptionTextFieldState.copy(value = value)
        )

        _uiState.value = _uiState.value.copy(
            listDescriptionTextFieldState = _uiState.value.listDescriptionTextFieldState.copy(
                isError = value.isBlank()
            )
        )
    }

    fun updateSelectedColor(color: ColorState) {
        val colorPickerState = _uiState.value.colorPickerState
        _uiState.value =
            _uiState.value.copy(colorPickerState = colorPickerState.copy(colorSelected = color))
    }

    fun updateSelectedIcon(icon: IconState) {
        val iconPickerState = _uiState.value.iconPickerState
        _uiState.value =
            _uiState.value.copy(iconPickerState = iconPickerState.updateSelectedIcon(icon))
    }

    fun saveList() {
        val name = uiState.value.listNameTextFieldState.value
        val description = uiState.value.listDescriptionTextFieldState.value
        val colorId = uiState.value.colorPickerState.colorSelected.colorId
        val iconId = uiState.value.iconPickerState.icons.first { it.isSelected }.iconId

        if (name.isBlank() || description.isBlank()) {

            _uiState.value = _uiState.value.copy(
                listNameTextFieldState = _uiState.value.listNameTextFieldState.copy(isError = name.isBlank())
            )

            _uiState.value = _uiState.value.copy(
                listDescriptionTextFieldState = _uiState.value.listDescriptionTextFieldState.copy(
                    isError = description.isBlank()
                )
            )
            return
        }

        val trimmedName = name.trimStart().trimEnd()
        val trimmedDescription = description.trimStart().trimEnd()

        viewModelScope.launch {
            if (isEditing()) {
                listRepository.updateList(
                    listId,
                    trimmedName,
                    trimmedDescription,
                    colorId,
                    iconId
                )
            } else {
                listRepository.createList(trimmedName, trimmedDescription, colorId, iconId)
            }
        }
    }

    fun isEditing(): Boolean = listId != 0L

}