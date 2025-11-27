package com.example.tanalista.ui.views

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
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
import com.example.tanalista.ui.dialogs.CartDialog
import com.example.tanalista.ui.dialogs.DeleteListItemDialog
import com.example.tanalista.ui.theme.BackgroundColor
import com.example.tanalista.ui.theme.ButtonBackground
import com.example.tanalista.ui.theme.Error
import com.example.tanalista.ui.theme.GrayBackground
import com.example.tanalista.ui.theme.Green
import com.example.tanalista.ui.theme.Purple
import com.example.tanalista.ui.theme.ToggleButtonCartChecked
import com.example.tanalista.ui.theme.ToggleButtonCartDisable
import com.example.tanalista.ui.theme.ToggleButtonListChecked
import com.example.tanalista.ui.theme.ToggleButtonListDisabled
import com.example.tanalista.ui.theme.White
import androidx.compose.ui.draw.shadow
import com.example.tanalista.viewmodel.CartViewModel
import com.example.tanalista.viewmodel.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.viewmodel.dialog.ListDialogViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartView(
    cartViewModel: CartViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { listDialogViewModel.openDialog() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Icon"
                )
            }
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ButtonBackground)
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
    Column(modifier = Modifier.padding(24.dp, 32.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "R$ %.2f".format(cartViewModel.totalValue),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BackgroundColor
                )
                Text(text = "Cart value", fontSize = 16.sp, color = BackgroundColor)
            }

            ExposedDropdownMenuBox(
                expanded = cartViewModel.isSortDropdownExpanded,
                onExpandedChange = {
                    cartViewModel.isSortDropdownExpanded =
                        !cartViewModel.isSortDropdownExpanded
                }
            ) {
                Row(
                    modifier = Modifier
                        .width(150.dp)
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                    horizontalArrangement = Arrangement.End
                )
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_order_by),
                        contentDescription = "Icon",
                        tint = White,
                    )
                }
                ExposedDropdownMenu(
                    expanded = cartViewModel.isSortDropdownExpanded,
                    onDismissRequest = { cartViewModel.isSortDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Category") },
                        onClick = {
                            cartViewModel.orderItemsBy(0)
                            cartViewModel.isSortDropdownExpanded = false
                        },
                        trailingIcon = {
                            if (cartViewModel.isCategoryOrderSelected) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = null,
                                    tint = Purple,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Alphabetical") },
                        onClick = {
                            cartViewModel.orderItemsBy(1)
                            cartViewModel.isSortDropdownExpanded = false
                        },
                        trailingIcon = {
                            if (cartViewModel.isAlphabeticalOrderSelected) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = null,
                                    tint = Purple,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            HeaderToggleButton(
                text = "List",
                modifier = Modifier.weight(1f),
                colors = IconToggleButtonColors(
                    containerColor = Green,
                    checkedContentColor = ToggleButtonListChecked,
                    contentColor = Green,
                    disabledContainerColor = GrayBackground,
                    disabledContentColor = ToggleButtonListDisabled,
                    checkedContainerColor = ToggleButtonListChecked
                ),
                textColor = ButtonBackground,
                iconResourceId = R.drawable.ic_list,
                iconTint = ButtonBackground,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        cartViewModel.showAllProductsInList()
                    }
                },
                isChecked = cartViewModel.isListToggleButtonChecked
            )

            HeaderToggleButton(
                text = "Cart",
                modifier = Modifier.weight(1f),
                colors = IconToggleButtonColors(
                    containerColor = Purple,
                    checkedContentColor = ToggleButtonCartChecked,
                    contentColor = Purple,
                    disabledContainerColor = ToggleButtonCartDisable,
                    disabledContentColor = White,
                    checkedContainerColor = ToggleButtonCartChecked
                ),
                textColor = White,
                iconResourceId = R.drawable.ic_cart,
                iconTint = White,
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
                color = White,
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (isEmpty) Arrangement.Center else Arrangement.Top,
    ) {
        productItems?.let { product ->
            if (isEmpty) {
                EmptyCartSection()
            } else {
                LazyColumn(content = {
                    itemsIndexed(product) { index, item ->
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
        } ?: EmptyCartSection()
    }
}

@Composable
fun HeaderToggleButton(
    text: String,
    modifier: Modifier,
    colors: IconToggleButtonColors,
    textColor: Color = White,
    iconResourceId: Int,
    iconTint: Color = White,
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
            .background(color = GrayBackground, shape = RoundedCornerShape(20.dp))
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
                tint = Purple
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = "$title ($quantity un)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ButtonBackground
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
fun EmptyCartSection() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(R.drawable.empty_cart), contentDescription = "Empty cart")

        Text(
            text = "Please click on the below button to add items in your list!",
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
                containerColor = Purple
            ), onClick = addItemToCartList
        )
        {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.ic_cart_in),
                contentDescription = "App Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CartOutButton(addItemToCartList: () -> Unit, isInCart: Boolean) {
    if (isInCart) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Error
            ), onClick = addItemToCartList
        )
        {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.ic_cart_out),
                contentDescription = "App Icon",
                tint = Color.White
            )
        }
    }
}
