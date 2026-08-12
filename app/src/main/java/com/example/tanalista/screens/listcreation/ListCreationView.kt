package com.example.tanalista.screens.listcreation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.screens.listcreation.model.ListCreationScreenState
import com.example.tanalista.dsm.colorpicker.model.ColorState
import com.example.tanalista.dsm.footer.Footer
import com.example.tanalista.dsm.iconpicker.model.IconState

@Composable
fun ListCreationView(viewModel: ListCreationViewModel, onBackAction: () -> Unit) {

    val uiState = viewModel.uiState.value

    LaunchedEffect(viewModel) {
        Log.d("Lucas", "LaunchedEffect")
        viewModel.initializeScreenData()
    }

    Scaffold(
        topBar = {
            HeaderListCreationView(
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
                state = uiState.footer,
                onCancelAction = {
                    onBackAction()
                },
                onConfirmAction = {
                    viewModel.createList()
                }
            )
        }
    )
}

@Composable
fun HeaderListCreationView(modifier: Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            "Create a new list",
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
        TextField(
            value = state.listName,
            onValueChange = { onListNameChanged(it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.backgroundSection),
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = colorResource(R.color.backgroundSection),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.description,
            onValueChange = { onListDescriptionChanged(it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.backgroundSection),
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = colorResource(R.color.backgroundSection),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormSection(sectionName = state.colorPickerState.sectionTitle) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                state.colorPickerState.colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(color = colorResource(color.colorId))
                            .border(
                                width = if (color.isSelected) 2.dp else 0.dp,
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .clickable { onSelectColor(color) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FormSection(sectionName = state.iconPickerState.sectionTitle) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.iconPickerState.icons) { icon ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (icon.isSelected) Color.LightGray else Color.Transparent
                            )
                            .clickable { onSelectIcon(icon) }
                    ) {
                        Icon(painter = painterResource(icon.iconId), contentDescription = null)
                    }
                }
            }
        }

    }
}

@Composable
fun FormSection(
    sectionName: String,
    content: @Composable (() -> Unit) = {}
) {
    Column {

        Text(text = sectionName)

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .background(
                    colorResource(R.color.backgroundSection),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}
