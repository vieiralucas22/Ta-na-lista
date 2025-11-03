package com.example.tanalista.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.ui.theme.BackgroundColor
import com.example.tanalista.ui.theme.ButtonBackground
import com.example.tanalista.ui.theme.Green
import com.example.tanalista.ui.theme.Purple
import com.example.tanalista.ui.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartView() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = "Adicionar"
                )
            }
        }, content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ButtonBackground)
            ) {
                HeaderHome()
                ListHome()
            }
        }
    )
}

@Composable
fun HeaderHome() {
    Column(modifier = Modifier.padding(24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = "R$ 1000.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BackgroundColor
                )
                Text(text = "Cart value", fontSize = 16.sp, color = BackgroundColor)
            }

        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            HeaderSubPageButton(
                text = "List",
                modifier = Modifier.weight(1f),
                buttonColors = ButtonDefaults.buttonColors(containerColor = Green),
                textColor = ButtonBackground,
                iconResourceId = R.drawable.ic_list,
                iconTint = ButtonBackground
            )
            HeaderSubPageButton(
                text = "Cart",
                modifier = Modifier.weight(1f),
                buttonColors = ButtonDefaults.buttonColors(containerColor = Purple),
                iconResourceId = R.drawable.ic_cart
            )
        }

    }
}

@Composable
fun ListHome() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackgroundColor,
                shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)
            )
            .padding(24.dp)
    ) { }
}

@Composable
fun HeaderSubPageButton(
    text: String,
    modifier: Modifier,
    buttonColors: ButtonColors,
    textColor: Color = White,
    iconResourceId: Int,
    iconTint: Color = White
) {
    Button(modifier = modifier, colors = buttonColors, onClick = {}) {
        Row (modifier = Modifier.padding(0.dp, 4.dp)){
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(iconResourceId),
                contentDescription = "Icon",
                tint = iconTint
            )
            Spacer(Modifier.width(10.dp))
            Text(text = text, color = textColor, fontSize = 16.sp)
        }
    }
}


@Preview
@Composable
fun CartViewPreview() {
    CartView()
}