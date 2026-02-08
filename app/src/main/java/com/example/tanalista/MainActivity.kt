package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.example.tanalista.ui.views.CartView
import com.example.tanalista.ui.viewmodel.CartViewModel
import com.example.tanalista.ui.viewmodel.dialog.DeleteListItemDialogViewModel
import com.example.tanalista.ui.viewmodel.dialog.ListDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartViewModel : CartViewModel by viewModels()
        val listDialogViewModel : ListDialogViewModel by viewModels()
        val deleteDialogViewModel : DeleteListItemDialogViewModel by viewModels()

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