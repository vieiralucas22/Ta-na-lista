package com.example.tanalista.screens

object Routes {

    /* Cart screen */
    const val CART_SCREEN = "CART_SCREEN/{listId}"
    fun cartScreen(listId: Long) = "CART_SCREEN/$listId"

    /* Home screen */
    const val HOME_SCREEN = "HOME_SCREEN"

    /* List Creation screen */
    const val LIST_CREATION_SCREEN = "LIST_CREATION_SCREEN/{listId}"
    fun listCreationScreen(listId: Long) = "LIST_CREATION_SCREEN/$listId"

}