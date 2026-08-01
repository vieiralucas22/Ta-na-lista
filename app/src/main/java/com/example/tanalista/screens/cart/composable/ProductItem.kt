package com.example.tanalista.screens.cart.composable

import androidx.compose.foundation.clickable
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
import com.example.tanalista.enums.ProductCategory

@Composable
fun ProductItem(
    title: String = "",
    price: Double = 0.0,
    category: String = "",
    onCartButtonClick: () -> Unit = {},
    quantity: Int = 0,
    isInCart: Boolean = false,
    onClick: () -> Unit = {},
    onDismissItem: () -> Unit = {}
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.StartToEnd
            || swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart
        ) {
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
                        painter = painterResource(getCategoryIcon(category = category)),
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

                CartButton(isInCart, onCartButtonClick)
            }

        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CartButton(isInCart: Boolean, onClick: () -> Unit) {

    val colorId = if (isInCart) R.color.error else R.color.purple
    val iconId = if (isInCart) R.drawable.ic_cart_out else R.drawable.ic_cart_in

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(colorId)
        ), onClick = onClick
    )
    {
        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(iconId),
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
        category = "Drink",
        quantity = 1,
        isInCart = false
    )
}

@Preview
@Composable
fun ProductCartPreview() {
    ProductItem(
        title = "Apple",
        price = 27.0,
        category = "Food",
        quantity = 1,
        isInCart = true
    )
}

fun getCategoryIcon(category: String): Int {

    val enumCategory = ProductCategory.entries
        .firstOrNull { it.name.equals(category, ignoreCase = true) }

    return when (enumCategory) {
        ProductCategory.FOOD -> R.drawable.ic_food
        ProductCategory.DRINK -> R.drawable.ic_drink
        ProductCategory.CLEAN -> R.drawable.ic_clean
        ProductCategory.BARBECUE -> R.drawable.ic_barbecue
        ProductCategory.UTILITIES -> R.drawable.ic_utilities
        ProductCategory.TOILET -> R.drawable.ic_toilet
        else -> {
            R.drawable.ic_undefined
        }
    }
}