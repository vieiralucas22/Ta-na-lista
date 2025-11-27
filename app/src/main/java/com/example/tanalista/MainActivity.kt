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
import com.example.tanalista.viewmodel.CartViewModel
import com.example.tanalista.viewmodel.ProfileViewModel
import com.example.tanalista.viewmodel.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.viewmodel.dialog.ListDialogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartViewModel = ViewModelProvider.create(this)[CartViewModel::class.java]
        val listDialogViewModel = ViewModelProvider.create(this)[ListDialogViewModel::class.java]
        val deleteDialogViewModel = ViewModelProvider.create(this)[DeleteListItemDialogViewModel::class.java]
        val profileViewModel = ViewModelProvider.create(this)[ProfileViewModel::class.java]

        setContent {
            MyApplicationApp(cartViewModel, listDialogViewModel, deleteDialogViewModel, profileViewModel)
        }
    }
}

@Composable
fun MyApplicationApp(
    cartViewModel: CartViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel,
    profileViewModel: ProfileViewModel, ) {

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
            AppDestinations.HOME -> CartView(cartViewModel, listDialogViewModel, deleteDialogViewModel)
            AppDestinations.PROFILE -> ProfileView(profileViewModel)
        }
    }
}

enum class AppDestinations(
    val label: String,
    val resourceId: Int,
) {
    HOME("Home", R.drawable.ic_home),
    PROFILE("Profile", R.drawable.ic_profile),
}
