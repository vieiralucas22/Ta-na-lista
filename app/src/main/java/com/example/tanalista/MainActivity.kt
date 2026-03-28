package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.tanalista.screens.cart.CartView
import com.example.tanalista.screens.cart.CartScreenViewModel
import com.example.tanalista.screens.cart.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.screens.cart.dialog.CartDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartScreenViewModel : CartScreenViewModel by viewModels()
        val cartDialogViewModel : CartDialogViewModel by viewModels()
        val deleteDialogViewModel : DeleteListItemDialogViewModel by viewModels()

        setContent {
            MyApplicationApp(cartScreenViewModel, cartDialogViewModel, deleteDialogViewModel)
        }
    }
}

@Composable
fun MyApplicationApp(
    cartScreenViewModel: CartScreenViewModel,
    cartDialogViewModel: CartDialogViewModel,
    deleteDialogViewModel: DeleteListItemDialogViewModel) {

    CartView(cartScreenViewModel,cartDialogViewModel,deleteDialogViewModel)
}