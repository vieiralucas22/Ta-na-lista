package com.example.tanalista.screens.home.action

import com.example.tanalista.dsm.bottomsheet.BottomSheetButtonAction

data class EditAction(val listId: Long) : BottomSheetButtonAction()
data class DeleteAction(val listId: Long) : BottomSheetButtonAction()