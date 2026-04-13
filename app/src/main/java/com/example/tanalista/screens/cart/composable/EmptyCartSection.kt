package com.example.tanalista.screens.cart.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tanalista.R

@Composable
fun EmptyCartSection() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(R.drawable.empty_cart), contentDescription = "Empty cart")

        Text(
            text = stringResource(R.string.empty_cart_message),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Preview (showBackground = true)
@Composable
fun EmptyCartSectionPreview()
{
    EmptyCartSection()
}