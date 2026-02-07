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
import com.example.tanalista.ui.views.HomeView
import com.example.tanalista.ui.views.ProfileView
import com.example.tanalista.viewmodel.CartViewModel
import com.example.tanalista.viewmodel.HomeViewModel
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

        setContent {
            MyApplicationApp(cartViewModel, listDialogViewModel, deleteDialogViewModel)
        }
    }
}

@Composable
fun MyApplicationApp(
    cartViewModel: CartViewModel,
    listDialogViewModel: ListDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel) {

    CartView(cartViewModel,listDialogViewModel,deleteDialogViewModel)
}