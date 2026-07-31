package com.example.tanalista.screens.cart.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R

@Composable
fun ProductItem(
    title: String = "",
    price: Double = 0.0,
    iconResourceId: Int = 0,
    addItemToCartList: () -> Unit = {},
    removeItemToCartList: () -> Unit = {},
    quantity: Int = 0,
    isInCart: Boolean = false,
    onClick: () -> Unit = {},
    onDismissItem: () -> Unit = {}
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.StartToEnd
            || swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDismissItem()
        }
    }

    SwipeToDismissBox(state = swipeToDismissBoxState, backgroundContent = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick() }),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.grayBackground)
            )
        ) {

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(iconResourceId),
                        contentDescription = "Icon",
                        tint = colorResource(R.color.purple)
                    )

                    Spacer(Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "$title ($quantity un)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(R.color.buttonBackground)
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(text = "R$ %.2f".format(price), fontSize = 12.sp)
                    }
                }

                if (isInCart)
                    CartOutButton(removeItemToCartList)
                else
                    CartInButton(addItemToCartList)
            }

        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CartInButton(addItemToCartList: () -> Unit) {

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.purple)
        ), onClick = addItemToCartList
    )
    {

        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_cart_in),
            contentDescription = "App Icon",
            tint = colorResource(R.color.white)
        )
    }
}

@Composable
fun CartOutButton(removeItemToCartList: () -> Unit) {

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.error)
        ), onClick = removeItemToCartList
    )
    {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.ic_cart_out),
            contentDescription = "App Icon",
            tint = colorResource(R.color.white)
        )
    }
}

@Preview
@Composable
fun ProductListPreview() {
    ProductItem(
        title = "Coffee",
        price = 27.0,
        iconResourceId = R.drawable.ic_drink,
        quantity = 1,
        isInCart = false
    )
}

@Preview
@Composable
fun ProductCartPreview() {
    ProductItem(
        title = "Coffee",
        price = 27.0,
        iconResourceId = R.drawable.ic_drink,
        quantity = 1,
        isInCart = true
    )
}