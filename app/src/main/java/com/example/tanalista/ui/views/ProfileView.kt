package com.example.tanalista.ui.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.tanalista.R
import com.example.tanalista.ui.theme.ButtonBackground
import com.example.tanalista.ui.theme.GrayBackground
import com.example.tanalista.ui.theme.White
import com.example.tanalista.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileView(viewModel: ProfileViewModel) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.updateImage(uri)
    }

    Scaffold(
        modifier = Modifier
            .background(White)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Profile", fontSize = 32.sp)

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                viewModel.imageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(999.dp)),
                        contentScale = ContentScale.Crop
                    )
                } ?: Image(
                    painter = painterResource(R.drawable.avatar_default),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(text = "Lucas", fontSize = 18.sp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(color = GrayBackground, shape = RoundedCornerShape(20.dp))
                    .padding(4.dp)
            ) {

                ProfileOption("Edit information", R.drawable.ic_edit)
                ProfileOption("Change theme", R.drawable.ic_palette)
                ProfileOption("Change language", R.drawable.ic_world)

            }
        }
    }
}

@Composable
fun ProfileOption(text: String, resourceIconId: Int) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = ButtonBackground
        ), modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(resourceIconId),
                    contentDescription = "Icon"
                )
                Spacer(Modifier.width(24.dp))
                Text(text = text, fontSize = 16.sp)
            }

            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "arrow icon",
            )
        }
    }
}
