package com.example.tanalista.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.tanalista.R
import com.example.tanalista.screens.home.composable.ListComponentItem
import com.example.tanalista.screens.home.model.ListComponentState

@Composable
fun HomeView(viewModel: HomeViewModel) {

    Scaffold(
        topBar = {
            HeaderHome(
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .statusBarsPadding()
                    .padding(12.dp)
            )
        },
        content = { paddingValues ->
            ContentHome(
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .padding(paddingValues)
            )
        })
}

@Composable
fun HeaderHome(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Smarter shopping starts here!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContentHome(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Quick Access",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(12.dp))

        val items = listOf(
            ListComponentState(
                "Receive Payments",
                "Accept money from anyone anywhere.",
                Color(0xFFE3F2FD)
            ) {
                Icon(painterResource(R.drawable.ic_plus), contentDescription = null)
            },
            ListComponentState(
                "Share Link",
                "Send a link to get instantly",
                Color(0xFFE8F5E9)
            ) {
                Icon(painterResource(R.drawable.ic_plus), contentDescription = null)
            },
            ListComponentState(
                "View Cards",
                "Manage your virtual & physical cards.",
                Color(0xFFF3E5F5)
            ) {
                Icon(painterResource(R.drawable.ic_plus), contentDescription = null)
            },
            ListComponentState(
                "Send Money",
                "Send money locally & globally",
                Color(0xFFFFF3E0)
            ) {
                Icon(painterResource(R.drawable.ic_plus), contentDescription = null)
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(300.dp)
        ) {
            items(items) { item ->
                ListComponentItem(item)
            }
        }
    }
}

