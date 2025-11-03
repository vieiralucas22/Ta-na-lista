package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.tanalista.ui.views.CartView
import com.example.tanalista.ui.views.ChatView
import com.example.tanalista.ui.views.ProfileView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyApplicationApp()
        }
    }
}

@PreviewScreenSizes
@Composable
fun MyApplicationApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(it.resourceId),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }

                )
            }
        },
        ) {
        when (currentDestination) {
            AppDestinations.HOME -> CartView()
            AppDestinations.CHAT -> ChatView()
            AppDestinations.PROFILE -> ProfileView()
        }
    }
}

enum class AppDestinations(
    val label: String,
    val resourceId: Int,
) {
    CHAT("Chat", R.drawable.ic_chat),
    HOME("Home", R.drawable.ic_home),
    PROFILE("Profile", R.drawable.ic_profile),
}
