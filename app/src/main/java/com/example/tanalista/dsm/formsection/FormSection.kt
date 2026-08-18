package com.example.tanalista.dsm.formsection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R

@Composable
fun FormSection(
    sectionName: Int,
    content: @Composable (() -> Unit) = {}
) {
    Column {

        Text(text = stringResource(sectionName))

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