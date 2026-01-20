package com.example.tanalista.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.ui.theme.LightAppTitle
import com.example.tanalista.ui.theme.Purple
import com.example.tanalista.ui.theme.White
import com.example.tanalista.viewmodel.HomeViewModel

@Composable
fun HomeView(viewModel: HomeViewModel) {

    val allLists by viewModel.allLists.observeAsState()

    Scaffold(
        modifier = Modifier
            .background(White)
            .windowInsetsPadding(WindowInsets.statusBars),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.createANewList() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Icon"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(16.dp, 0.dp)) {

                TitleSection()

                Text(
                    text = "Your lists!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Purple
                )

                Spacer(modifier = Modifier.height(24.dp))

                allLists?.let { list ->
                    LazyColumn(content = {
                        itemsIndexed(list) { index, item ->
                            ListItem(item.name, "22/05/2002", R.drawable.ic_barbecue)
                        }
                    })
                } ?: EmptyListSection()
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Box(
                modifier = Modifier
                    .size(100.dp, 90.dp)
                    .background(Purple, shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 300.dp))
            )
        }
    }
}

@Composable
fun TitleSection() {
    Row(modifier = Modifier.padding(0.dp, 50.dp, 0.dp, 32.dp)) {
        Text(
            text = "Welcome,",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Purple
        )
        Text(
            text = " to Ta na Lista",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = LightAppTitle
        )
        Text(
            text = "!",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Purple
        )
    }
}

@Composable
fun ListItem(text: String, createdAt: String, resourceId: Int) {
    Button(
        contentPadding = PaddingValues(0.dp),
        shape = RectangleShape,
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple, shape = RoundedCornerShape(24.dp))
                .padding(12.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(resourceId),
                    contentDescription = "Food",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column()
                {
                    Text(
                        text = text,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                    Text(text = createdAt, color = White)
                }
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "Arrow",
                tint = White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun EmptyListSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.empty_cart), contentDescription = "Empty cart")

        Text(
            text = "Please click on the below button to create a new list!",
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
