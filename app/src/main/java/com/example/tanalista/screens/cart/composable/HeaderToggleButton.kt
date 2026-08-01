package com.example.tanalista.screens.cart.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.screens.cart.model.ButtonState
import com.example.tanalista.screens.cart.model.HeaderToggleButtonState

@Composable
fun HeaderToggleButton(
    state: HeaderToggleButtonState,
    modifier: Modifier,
    colors: IconToggleButtonColors,
    textColor: Color = colorResource(R.color.white),
    iconTint: Color = colorResource(R.color.white),
    onCheckedChange: (Boolean) -> Unit = {},
) {
    IconToggleButton(
        modifier = modifier.shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(12.dp)
        ),
        checked = state.isChecked,
        onCheckedChange = onCheckedChange,
        colors = colors,
    ) {
        Row(
            modifier = Modifier.padding(0.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(state.iconResourceId),
                contentDescription = "Icon",
                tint = iconTint
            )

            Spacer(Modifier.width(10.dp))

            Text(
                text = stringResource(state.buttonState.stringId),
                color = textColor,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(widthDp = 200, heightDp = 50)
@Composable
fun HeaderToggleButtonCartPreview() {

    HeaderToggleButton(
        state = HeaderToggleButtonState(
            R.drawable.ic_cart,
            false,
            ButtonState(R.string.cart)
        ),
        modifier = Modifier,
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = colorResource(R.color.purple),
        )
    )
}

@Preview(widthDp = 200, heightDp = 50)
@Composable
fun HeaderToggleButtonListPreview() {

    HeaderToggleButton(
        state = HeaderToggleButtonState(
            R.drawable.ic_list,
            false,
            ButtonState(R.string.list)
        ),
        modifier = Modifier,
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = colorResource(R.color.green),
        )
    )
}
