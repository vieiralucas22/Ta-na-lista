package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.tanalista.screens.cart.CartView
import com.example.tanalista.screens.cart.CartScreenViewModel
import com.example.tanalista.screens.cart.dialog.CartDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cartScreenViewModel : CartScreenViewModel by viewModels()
        val cartDialogViewModel : CartDialogViewModel by viewModels()

        setContent {
            CartView(cartScreenViewModel,cartDialogViewModel)
        }
    }
}