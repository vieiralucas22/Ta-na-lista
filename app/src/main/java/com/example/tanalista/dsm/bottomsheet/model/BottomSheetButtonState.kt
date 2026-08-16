package com.example.tanalista.dsm.bottomsheet.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tanalista.dsm.bottomsheet.BottomSheetButtonAction

data class BottomSheetButtonState(
    @StringRes
    val titleId: Int,
    @DrawableRes
    val iconId: Int,
    val action: BottomSheetButtonAction
)

