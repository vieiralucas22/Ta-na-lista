package com.example.tanalista.dsm.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R
import com.example.tanalista.dsm.textfield.model.TextFieldState

const val MAX_CHARS = 50

@Composable
fun TextFieldComponent(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    maxChars: Int = MAX_CHARS,
    maxLines: Int = 3,
    onValueChange: (String) -> Unit,
) {

    TextField(
        value = state.value,
        onValueChange = {
            if (it.length < maxChars)
                onValueChange(it)
        },
        label = { Text(text = stringResource(state.label)) },
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(R.color.backgroundSection),
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = colorResource(R.color.backgroundSection),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorContainerColor = colorResource(R.color.backgroundSection),
            errorTextColor = colorResource(R.color.error),
            errorCursorColor = colorResource(R.color.error),
        ),
        shape = RoundedCornerShape(16.dp),
        isError = state.isError,
        maxLines = maxLines,
    )
}