package com.example.tanalista.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R
import com.example.tanalista.screens.cart.dialog.CartDialog
import com.example.tanalista.screens.cart.dialog.DeleteListItemDialog
import androidx.compose.ui.res.colorResource
import com.example.tanalista.screens.cart.composable.CartPriceArea
import com.example.tanalista.screens.cart.composable.EmptyCartSection
import com.example.tanalista.screens.cart.composable.HeaderToggleButton
import com.example.tanalista.screens.cart.composable.ProductItem
import com.example.tanalista.screens.cart.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.screens.cart.dialog.CartDialogViewModel

@Composable
fun CartView(
    cartScreenViewModel: CartScreenViewModel,
    cartDialogViewModel: CartDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { cartDialogViewModel.openDialog() },
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
                HeaderCart(cartScreenViewModel)
                ListCart(cartScreenViewModel, cartDialogViewModel, deleteDialogViewModel)
                CartDialog(cartDialogViewModel)
                DeleteListItemDialog(deleteDialogViewModel)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderCart(cartScreenViewModel: CartScreenViewModel) {
    Column(modifier = Modifier.padding(24.dp, 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartPriceArea(cartScreenViewModel)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            HeaderToggleButton(
                state = cartScreenViewModel.state.listToggle,
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
                iconTint = colorResource(R.color.buttonBackground),
                onCheckedChange = { isChecked ->
                        cartScreenViewModel.showAllProductsInSection(!isChecked)
                },
            )

            HeaderToggleButton(
                state = cartScreenViewModel.state.cartToggle,
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
                iconTint = colorResource(R.color.white),
                onCheckedChange = { isChecked ->
                        cartScreenViewModel.showAllProductsInSection(isChecked)
                }
            )
        }

    }
}

@Composable
fun ListCart(
    cartScreenViewModel: CartScreenViewModel,
    cartDialogViewModel: CartDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel
) {
    val productItems by cartScreenViewModel.state.allProductsInList.collectAsState(emptyList())
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
        productItems.let { product ->
            if (isEmpty) {
                EmptyCartSection()
            } else {
                LazyColumn(content = {
                    items(product, key = { product -> product.productId }) { item ->
                        ProductItem(
                            item.name,
                            item.productPrice,
                            cartScreenViewModel.getCategoryIcon(item.category),
                            addItemToCartList =
                                {
                                    cartScreenViewModel.moveProductBetweenLists(item, true)
                                },
                            removeItemToCartList =
                                {
                                    cartScreenViewModel.moveProductBetweenLists(item, false)
                                },
                            item.quantity,
                            item.isInCart,
                            onClick = {
                                cartDialogViewModel.editDialog(item)
                            },
                            onLongClick = {
                                deleteDialogViewModel.openDialog(item)
                            }
                        )
                    }
                })
            }
        }
    }
}
