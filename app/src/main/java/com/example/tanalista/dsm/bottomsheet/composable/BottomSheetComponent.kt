package com.example.tanalista.dsm.bottomsheet.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R
import com.example.tanalista.dsm.bottomsheet.BottomSheetButtonAction
import com.example.tanalista.dsm.bottomsheet.model.BottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    state: BottomSheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onBottomSheetButtonAction: (BottomSheetButtonAction) -> Unit
) {
    if (!state.isVisible) return

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(),
        containerColor = colorResource(R.color.white),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            state.buttonList.forEach {
                ButtonSheetButton(it) { action ->
                    onBottomSheetButtonAction(action)
                }
            }
        }
    }
}
