package com.example.tanalista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tanalista.screens.Routes
import com.example.tanalista.screens.cart.CartScreenViewModel
import com.example.tanalista.screens.cart.CartView
import com.example.tanalista.screens.cart.dialog.CartDialogViewModel
import com.example.tanalista.screens.home.HomeView
import com.example.tanalista.screens.home.HomeViewModel
import com.example.tanalista.screens.listcreation.ListCreationView
import com.example.tanalista.screens.listcreation.ListCreationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val homeViewModel: HomeViewModel by viewModels()
        val cartViewModel: CartScreenViewModel by viewModels()
        val cartDialogViewModel: CartDialogViewModel by viewModels()
        val listCreationViewModel: ListCreationViewModel by viewModels()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Routes.LIST_CREATION_SCREEN,
                builder = {

                    composable(Routes.HOME_SCREEN)
                    {
                        HomeView(homeViewModel)
                    }

                    composable(Routes.LIST_CREATION_SCREEN)
                    {
                        ListCreationView(listCreationViewModel, onBackAction = {
                            navController.popBackStack()
                        })
                    }

                    composable(Routes.CART_SCREEN)
                    {
                        CartView(cartViewModel, cartDialogViewModel)
                    }

                })
        }
    }

}