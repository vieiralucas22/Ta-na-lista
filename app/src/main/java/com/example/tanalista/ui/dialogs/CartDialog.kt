package com.example.tanalista.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tanalista.ui.theme.ButtonBackground
import com.example.tanalista.ui.theme.Error
import com.example.tanalista.ui.theme.White
import com.example.tanalista.viewmodel.dialog.ListDialogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDialog(viewModel: ListDialogViewModel) {
    val categories by viewModel.allCategories.collectAsState()

    if (viewModel.isDialogOpen) {
        Dialog(onDismissRequest = { viewModel.closeDialog() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {

                Text(text = "Add a new item", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                OutlinedTextFieldDialog(
                    viewModel.productName, viewModel.isInvalidProductName, { viewModel.productName = it },
                    KeyboardType.Text, "Product name"
                )

                OutlinedTextFieldDialog(
                    viewModel.quantity,
                    viewModel.isInvalidQuantity,
                    { viewModel.handleWithQuantityValueChange(it) },
                    KeyboardType.Decimal,
                    "Quantity"
                )

                OutlinedTextFieldDialog(
                    viewModel.price,
                    viewModel.isInvalidPrice,
                    { viewModel.handleWithPriceValueChange(it) },
                    KeyboardType.Decimal,
                    "Price"
                )

                Spacer(Modifier.height(4.dp))

                ExposedDropdownMenuBox(
                    expanded = viewModel.isDropdownExpanded,
                    onExpandedChange = {
                        viewModel.isDropdownExpanded = !viewModel.isDropdownExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = viewModel.category,
                        onValueChange = {},
                        enabled = false,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isDropdownExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = White,
                            disabledLabelColor = ButtonBackground,
                            disabledIndicatorColor = ButtonBackground,
                            disabledTextColor = ButtonBackground
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = viewModel.isDropdownExpanded,
                        onDismissRequest = { viewModel.isDropdownExpanded = false }
                    ) {
                        categories.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.categoryName) },
                                onClick = {
                                    viewModel.category = option.categoryName
                                    viewModel.isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(text = "Add to cart?", fontWeight = FontWeight.SemiBold)

                    Checkbox(
                        modifier = Modifier.background(
                            color = Color.Transparent
                        ),
                        checked = viewModel.canAddToCart,
                        onCheckedChange = { viewModel.canAddToCart = it },
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        onClick = {
                            viewModel.addItemToList()
                        },
                    ) {
                        Text(text = "Add", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun OutlinedTextFieldDialog(
    value: String,
    isInvalid: Boolean,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = isInvalid,
        colors = TextFieldDefaults.colors(
            errorIndicatorColor = Error,
            errorContainerColor = White,
            unfocusedContainerColor = White,
            focusedContainerColor = White,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}
