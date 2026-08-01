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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tanalista.R
import com.example.tanalista.screens.cart.dialog.CartDialog
import androidx.compose.ui.res.colorResource
import com.example.tanalista.model.database.model.dto.ListItemDTO
import com.example.tanalista.screens.cart.composable.CartPriceArea
import com.example.tanalista.screens.cart.composable.EmptyCartSection
import com.example.tanalista.screens.cart.composable.HeaderToggleButton
import com.example.tanalista.screens.cart.composable.ProductComponentItem
import com.example.tanalista.screens.cart.dialog.CartDialogViewModel
import com.example.tanalista.screens.cart.model.CartScreenState

@Composable
fun CartView(
    cartScreenViewModel: CartScreenViewModel,
    cartDialogViewModel: CartDialogViewModel
) {
    val uiState = cartScreenViewModel.uiState.collectAsState().value

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
        },
        topBar = {
            HeaderScreen(
                Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                uiState,
                onListCheckedChange = { isListChecked ->
                    if (isListChecked)
                        cartScreenViewModel.showAllProductsInList()
                },
                onCartCheckedChange = { isCartChecked ->
                    if (isCartChecked)
                        cartScreenViewModel.showAllProductsInCart()
                })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.buttonBackground))
                    .padding(paddingValues)
            ) {

                ContentScreen(
                    uiState,
                    onOpenEditDialog = { item -> cartDialogViewModel.editDialog(item) },
                    onMoveItem = { item, moveToCart ->
                        cartScreenViewModel.moveProductBetweenLists(item, moveToCart)
                    },
                    onDismissItem = { item ->
                        cartScreenViewModel.deleteListItem(item)
                    }
                )

                CartDialog(cartDialogViewModel)

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderScreen(
    modifier: Modifier,
    state: CartScreenState,
    onListCheckedChange: (Boolean) -> Unit,
    onCartCheckedChange: (Boolean) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CartPriceArea(state.totalCartValue)
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            HeaderToggleButton(
                state = state.listToggle,
                modifier = Modifier.weight(1f),
                colors = IconButtonDefaults.iconToggleButtonColors(
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
                    onListCheckedChange(isChecked)
                },
            )

            HeaderToggleButton(
                state = state.cartToggle,
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
                    onCartCheckedChange(isChecked)
                }
            )
        }
    }
}

@Composable
fun ContentScreen(
    uiState: CartScreenState,
    onOpenEditDialog: (ListItemDTO) -> Unit,
    onMoveItem: (ListItemDTO, Boolean) -> Unit,
    onDismissItem: (ListItemDTO) -> Unit
) {
    val productItems by uiState.allProductsInList.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (productItems.isEmpty()) Arrangement.Center else Arrangement.Top,
    ) {
        productItems.let { product ->
            if (productItems.isEmpty()) {
                EmptyCartSection()
            } else {
                LazyColumn(content = {
                    items(product, key = { product -> product.productId }) { item ->
                        ProductComponentItem(
                            item.name,
                            item.productPrice,
                            item.category,
                            onCartButtonClick = {
                                onMoveItem(item, !item.isInCart)
                            },
                            item.quantity,
                            item.isInCart,
                            onClick = {
                                onOpenEditDialog(item)
                            },
                            onDismissItem = {
                                onDismissItem(item)
                            }
                        )
                    }
                })
            }
        }
    }
}

