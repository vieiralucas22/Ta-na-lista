package com.example.tanalista.dsm.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.dsm.colorpicker.model.ColorPickerState
import com.example.tanalista.dsm.colorpicker.model.ColorState
import com.example.tanalista.dsm.formsection.FormSection

@Composable
fun ColorPiker(state: ColorPickerState, onSelectColor: (ColorState) -> Unit) {
    FormSection(sectionName = state.sectionTitle) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            state.colors.forEach { color ->
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
}

