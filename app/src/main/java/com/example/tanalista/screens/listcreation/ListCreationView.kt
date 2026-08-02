package com.example.tanalista.screens.listcreation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R

@Composable
fun ListCreationView(viewModel: ListCreationViewModel) {

    Scaffold(
        topBar = {
            HeaderListCreationView(
                modifier =
                    Modifier
                        .background(colorResource(R.color.white))
                        .statusBarsPadding()
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
            )
        },
        content = { paddingValues ->
            ContentListCreationView(
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .padding(
                        horizontal = 24.dp,
                        vertical = paddingValues.calculateTopPadding()
                    )
                    .fillMaxSize()
            )
        },
        bottomBar = {
            FooterListCreationView(
                modifier =
                    Modifier
                        .background(colorResource(R.color.white))
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .fillMaxWidth()
            )
        }
    )
}

@Composable
fun HeaderListCreationView(modifier: Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            "Create a new list",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContentListCreationView(modifier: Modifier = Modifier) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFFE3F2FD)) }
    var selectedIcon by remember { mutableStateOf(Icons.Default.ShoppingCart) }

    Column(modifier = modifier.padding(top = 16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.backgroundSection),
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = colorResource(R.color.backgroundSection),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(R.color.backgroundSection),
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = colorResource(R.color.backgroundSection),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        FormSection(sectionName = "Color") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                val colors = listOf(
                    Color(0xFFE3F2FD),
                    Color(0xFFE8F5E9),
                    Color(0xFFFFF3E0),
                    Color(0xFFFCE4EC),
                    Color(0xFFF3E5F5),
                    Color(0xFFE0F7FA)
                )

                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (color == selectedColor) 2.dp else 0.dp,
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = color }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FormSection(sectionName = "Icon") {

            val icons = listOf(
                Icons.Default.ShoppingCart,
                Icons.Default.Home,
                Icons.Default.Favorite,
                Icons.Default.Book,
                Icons.Default.Fastfood,
                Icons.Default.Medication,
                Icons.Default.LocalMall,
                Icons.Default.Pets
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(icons) { icon ->

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (icon == selectedIcon) Color.LightGray else Color.Transparent
                            )
                            .clickable { selectedIcon = icon }
                    ) {
                        Icon(icon, contentDescription = null)
                    }
                }
            }
        }

    }
}

@Composable
fun FooterListCreationView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 50.dp)
        ) {
            Text("Cancel")
        }

        Button(
            onClick = {
            },
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 50.dp)
        ) {
            Text("Create")
        }
    }
}

@Composable
fun FormSection(
    sectionName: String,
    content: @Composable (() -> Unit) = {}
) {
    Column {

        Text(text = sectionName)

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .background(
                    colorResource(R.color.backgroundSection),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}
