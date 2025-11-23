package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import com.example.tanalista.ui.views.CartView
import com.example.tanalista.ui.views.ProfileView
import com.example.tanalista.viewmodel.HomeViewModel
import com.example.tanalista.viewmodel.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.viewmodel.dialog.ListDialogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val homeViewModel = ViewModelProvider.create(this)[HomeViewModel::class.java]
        val listDialogViewModel = ViewModelProvider.create(this)[ListDialogViewModel::class.java]
        val deleteDialogViewModel = ViewModelProvider.create(this)[DeleteListItemDialogViewModel::class.java]

        setContent {
            MyApplicationApp(homeViewModel, listDialogViewModel, deleteDialogViewModel)
        }
    }
}

@Composable
fun MyApplicationApp(
    homeViewModel: HomeViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel, ) {

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
            AppDestinations.HOME -> CartView(homeViewModel, listDialogViewModel, deleteDialogViewModel)
//            AppDestinations.CHAT -> ChatView()
//            AppDestinations.PROFILE -> ProfileView()
        }
    }
}

enum class AppDestinations(
    val label: String,
    val resourceId: Int,
) {
    //CHAT("Chat", R.drawable.ic_chat),
    HOME("Home", R.drawable.ic_home),
//    PROFILE("Profile", R.drawable.ic_profile),
}
