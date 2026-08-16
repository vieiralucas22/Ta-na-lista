package com.example.tanalista.screens.home.model

import com.example.tanalista.R
import com.example.tanalista.dsm.bottomsheet.model.BottomSheetState

data class HomeUiState(
    val listState: List<ListComponentState>,
    val bottomSheetState: BottomSheetState
){
    companion object {
        val DEFAULT_STATE = HomeUiState(
            listState = listOf(
                ListComponentState(
                    id = 0L,
                    name = "",
                    description = "",
                    colorId = R.color.light_blue,
                    iconId = R.drawable.ic_shopping_cart_fill
                )
            ),
            bottomSheetState = BottomSheetState(
                isVisible = false,
                buttonList = emptyList()
            )
        )
    }
}
