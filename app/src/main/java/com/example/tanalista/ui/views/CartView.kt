package com.example.tanalista.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.ui.views.dialogs.CartDialog
import com.example.tanalista.ui.views.dialogs.DeleteListItemDialog
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.tanalista.ui.viewmodel.CartViewModel
import com.example.tanalista.ui.viewmodel.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.ui.viewmodel.dialog.ListDialogViewModel

@Composable
fun CartView(
    cartViewModel: CartViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { listDialogViewModel.openDialog() },
                containerColor = colorResource(R.color.floatingButton)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Icon",
                    tint = colorResource(R.color.white)
                )
            }
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.buttonBackground))
                    .padding(paddingValues)
            ) {
                HeaderCart(cartViewModel)
                ListCart(cartViewModel, listDialogViewModel, deleteDialogViewModel)
                CartDialog(listDialogViewModel)
                DeleteListItemDialog(deleteDialogViewModel)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderCart(cartViewModel: CartViewModel) {
    Column(modifier = Modifier.padding(24.dp, 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartPriceArea(cartViewModel)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            HeaderToggleButton(
                text = stringResource(R.string.list),
                modifier = Modifier.weight(1f),
                colors = IconToggleButtonColors(
                    containerColor = colorResource(R.color.green),
                    checkedContentColor = colorResource(R.color.toggleButtonListChecked),
                    contentColor = colorResource(R.color.green),
                    disabledContainerColor = colorResource(R.color.grayBackground),
                    disabledContentColor = colorResource(R.color.toggleButtonListDisabled),
                    checkedContainerColor = colorResource(R.color.toggleButtonListChecked)
                ),
                textColor = colorResource(R.color.buttonBackground),
                iconResourceId = R.drawable.ic_list,
                iconTint = colorResource(R.color.buttonBackground),
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        cartViewModel.showAllProductsInList()
                    }
                },
                isChecked = cartViewModel.isListToggleButtonChecked
            )

            HeaderToggleButton(
                text = stringResource(R.string.cart),
                modifier = Modifier.weight(1f),
                colors = IconToggleButtonColors(
                    containerColor = colorResource(R.color.purple),
                    checkedContentColor = colorResource(R.color.toggleButtonCartChecked),
                    contentColor = colorResource(R.color.purple),
                    disabledContainerColor = colorResource(R.color.toggleButtonCartDisable),
                    disabledContentColor = colorResource(R.color.white),
                    checkedContainerColor = colorResource(R.color.toggleButtonCartChecked)
                ),
                textColor = colorResource(R.color.white),
                iconResourceId = R.drawable.ic_cart,
                iconTint = colorResource(R.color.white),
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        cartViewModel.showAllProductsInCart()
                    }
                },
                isChecked = cartViewModel.isCartToggleButtonChecked
            )
        }

    }
}

@Composable
fun ListCart(
    cartViewModel: CartViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel
) {
    val productItems by cartViewModel.allProductsInCurrentPage.observeAsState()
    val isEmpty = productItems.isNullOrEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (isEmpty) Arrangement.Center else Arrangement.Top,
    ) {
        productItems?.let { product ->
            if (isEmpty) {
                EmptyCartSection(cartViewModel)
            } else {
                LazyColumn(content = {
                    items(product, key = { product -> product.productId }) { item ->
                        ProductItem(
                            item.name,
                            item.productPrice,
                            cartViewModel.getCategoryIcon(item.category),
                            addItemToCartList =
                                {
                                    cartViewModel.moveProductBetweenLists(item, true)
                                },
                            removeItemToCartList =
                                {
                                    cartViewModel.moveProductBetweenLists(item, false)
                                },
                            item.quantity,
                            item.isInCart,
                            onClick = {
                                listDialogViewModel.editDialog(item)
                            },
                            onLongClick = {
                                deleteDialogViewModel.openDialog(item)
                            }
                        )
                    }
                })
            }
        } ?: EmptyCartSection(cartViewModel)
    }
}

@Composable
fun HeaderToggleButton(
    text: String,
    modifier: Modifier,
    colors: IconToggleButtonColors,
    textColor: Color = colorResource(R.color.white),
    iconResourceId: Int,
    iconTint: Color = colorResource(R.color.white),
    onCheckedChange: (Boolean) -> Unit,
    isChecked: Boolean
) {
    IconToggleButton(
        modifier = modifier.shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(12.dp)
        ),
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = colors,
    ) {
        Row(
            modifier = Modifier.padding(0.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(iconResourceId),
                contentDescription = "Icon",
                tint = iconTint
            )
            Spacer(Modifier.width(10.dp))
            Text(text = text, color = textColor, fontSize = 18.sp)
        }
    }
}

@Composable
fun ProductItem(
    title: String = "",
    price: Double = 0.0,
    iconResourceId: Int = 0,
    addItemToCartList: () -> Unit,
    removeItemToCartList: () -> Unit,
    quantity: Int = 0,
    isInCart: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(0.dp, 0.dp, 0.dp, 4.dp)
            .background(
                color = colorResource(R.color.grayBackground),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

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

        CartInButton(addItemToCartList, isInCart)
        CartOutButton(removeItemToCartList, isInCart)
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun EmptyCartSection(cartViewModel: CartViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(R.drawable.empty_cart), contentDescription = "Empty cart")

        Text(
            text = stringResource(R.string.empty_cart_message),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CartInButton(addItemToCartList: () -> Unit, isInCart: Boolean) {
    if (!isInCart) {
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
}

@Composable
fun CartOutButton(addItemToCartList: () -> Unit, isInCart: Boolean) {
    if (isInCart) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.error)
            ), onClick = addItemToCartList
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
}

@Composable
fun CartPriceArea(cartViewModel: CartViewModel) {
    Column {
        Text(
            text = "R$ %.2f".format(cartViewModel.totalValue),
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
