package com.example.tanalista.dsm.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R
import com.example.tanalista.dsm.footer.model.FooterState

@Composable
fun Footer(
    state: FooterState,
    modifier: Modifier = Modifier,
    onCancelAction: () -> Unit,
    onConfirmAction: () -> Unit
) {
    Row(
        modifier = modifier
            .background(colorResource(R.color.white))
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = {
                onCancelAction()
            },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 50.dp)
        ) {
            Text(text = stringResource(state.outlineButtonState.stringId))
        }

        Button(
            onClick = {
                onConfirmAction()
            },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 50.dp)
        ) {
            Text(text = stringResource(state.fillButtonState.stringId))
        }
    }
}