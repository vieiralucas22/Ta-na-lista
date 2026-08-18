package com.example.tanalista.dsm.iconpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.dsm.formsection.FormSection
import com.example.tanalista.dsm.iconpicker.model.IconPickerState
import com.example.tanalista.dsm.iconpicker.model.IconState

@Composable
fun IconPicker(state: IconPickerState, onSelectIcon: (IconState) -> Unit) {
    FormSection(sectionName = state.sectionTitle) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.icons) { iconState ->
                val isSelected = state.iconSelected == iconState
                IconButton(iconState, isSelected) {
                    onSelectIcon(iconState)
                }
            }
        }
    }
}

@Composable
fun IconButton(state: IconState, isSelected: Boolean, onSelect: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) Color.LightGray else Color.Transparent
            )
            .clickable { onSelect() }
    ) {
        Icon(painter = painterResource(state.iconId), contentDescription = null)
    }
}

