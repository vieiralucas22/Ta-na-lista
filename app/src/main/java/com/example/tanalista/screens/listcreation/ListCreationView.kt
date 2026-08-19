package com.example.tanalista.screens.listcreation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.dsm.colorpicker.ColorPiker
import com.example.tanalista.screens.listcreation.model.ListCreationScreenState
import com.example.tanalista.dsm.colorpicker.model.ColorState
import com.example.tanalista.dsm.footer.Footer
import com.example.tanalista.dsm.iconpicker.IconPicker
import com.example.tanalista.dsm.iconpicker.model.IconState
import com.example.tanalista.dsm.textfield.TextFieldComponent

@Composable
fun ListCreationView(viewModel: ListCreationViewModel, onBackAction: () -> Unit) {

    val uiState = viewModel.uiState.value

    LaunchedEffect(viewModel) {
        viewModel.initializeScreenData()
    }

    Scaffold(
        topBar = {
            HeaderListCreationView(
                isEditing = viewModel.isEditing(),
                modifier =
                    Modifier
                        .background(colorResource(R.color.white))
                        .statusBarsPadding()
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
            )
        },
        content = { paddingValues ->
            ContentListCreationView(
                state = uiState,
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .padding(
                        horizontal = 24.dp,
                        vertical = paddingValues.calculateTopPadding()
                    )
                    .fillMaxSize(),
                onListNameChanged = viewModel::updateListName,
                onListDescriptionChanged = viewModel::updateListDescription,
                onSelectColor = viewModel::updateSelectedColor,
                onSelectIcon = viewModel::updateSelectedIcon
            )
        },
        bottomBar = {
            Footer(
                state = uiState.footerState,
                onCancelAction = {
                    onBackAction()
                },
                onConfirmAction = {
                    if (viewModel.saveList())
                        onBackAction()
                }
            )
        }
    )
}

@Composable
fun HeaderListCreationView(modifier: Modifier, isEditing: Boolean = false) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            text = getTitle(isEditing),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContentListCreationView(
    modifier: Modifier = Modifier,
    state: ListCreationScreenState,
    onListNameChanged: (String) -> Unit,
    onListDescriptionChanged: (String) -> Unit,
    onSelectColor: (ColorState) -> Unit,
    onSelectIcon: (IconState) -> Unit,
) {
    Column(modifier = modifier.padding(top = 16.dp)) {

        TextFieldComponent(
            state.listNameTextFieldState,
            maxChars = 20,
            maxLines = 1
        ) { onListNameChanged(it) }

        Spacer(modifier = Modifier.height(16.dp))

        TextFieldComponent(state.listDescriptionTextFieldState) { onListDescriptionChanged(it) }

        Spacer(modifier = Modifier.height(20.dp))

        ColorPiker(state.colorPickerState) { color ->
            onSelectColor(color)
        }

        Spacer(modifier = Modifier.height(20.dp))

        IconPicker(state.iconPickerState) { icon ->
            onSelectIcon(icon)
        }

    }
}

@Composable
fun getTitle(isEditing: Boolean): String =
    if (isEditing) stringResource(R.string.edit_list) else stringResource(R.string.create_a_new_list)
