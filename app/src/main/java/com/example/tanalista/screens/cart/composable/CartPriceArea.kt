package com.example.tanalista.screens.cart.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tanalista.R

@Composable
fun CartPriceArea(totalCartValue: Double) {
    Column {
        Text(
            text = "R$ %.2f".format(totalCartValue),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.backgroundColor)
        )
        Text(
            text = stringResource(R.string.cart_value),
            fontSize = 16.sp,
            color = colorResource(R.color.backgroundColor)
        )
    }
}
@Preview(showBackground = true, backgroundColor = 0xFF0A0A1A)
@Composable
fun CartPriceAreaPreview() {
    CartPriceArea(450.0)
}