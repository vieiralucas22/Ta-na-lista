package com.example.tanalista.screens.home.model

import com.example.tanalista.R

data class HomeUiState(
    val listState: List<ListComponentState>
){
    companion object {
        val DEFAULT_STATE = HomeUiState (
            listState = listOf(
                ListComponentState(
                    name = "",
                    description = "",
                    colorId = R.color.light_blue,
                    iconId = R.drawable.ic_shopping_cart_fill
                )
            )
        )
    }
}
