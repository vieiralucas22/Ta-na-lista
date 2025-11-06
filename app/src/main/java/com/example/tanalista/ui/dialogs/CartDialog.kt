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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tanalista.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDialog(viewModel: HomeViewModel) {
    var productName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val options = listOf("Alimentos", "Higiene", "Limpeza", "Bebidas", "Outros")
    var selectedOption by remember { mutableStateOf(options[0]) }
    var canAddToCart by remember { mutableStateOf(false) }

    if (viewModel.isDialogOpen) {
        Dialog(onDismissRequest = { viewModel.closeDialog() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {

                Text(text = "Add a new item", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text(text = "Product name") }
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text(text = "Quantity")},
                    keyboardOptions = KeyboardOptions (
                        keyboardType = KeyboardType.Decimal,
                    )
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = "Price") },
                    keyboardOptions = KeyboardOptions (
                        keyboardType = KeyboardType.Decimal,
                    )
                )

                Spacer(Modifier.height(4.dp))

                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedOption,
                        onValueChange = {},
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedOption = option
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End){

                    Text(text = "Add to cart?", fontWeight = FontWeight.SemiBold)

                    Checkbox(
                        modifier = Modifier.background(
                            color = Color.Transparent
                        ),
                        checked = canAddToCart,
                        onCheckedChange = {canAddToCart = it},
                    )
                }

                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(modifier = Modifier.padding(8.dp).weight(1f), onClick = {},) {
                        Text(text = "Save", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
