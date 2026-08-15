package com.example.tanalista.screens.home.converter

import com.example.tanalista.model.database.model.ListEntity
import com.example.tanalista.screens.home.model.ListComponentState

class ListComponentConverter {

    fun convert(allLists: List<ListEntity>): List<ListComponentState> =
        allLists.map { list ->
            ListComponentState(
                name = list.name,
                description = list.description,
                colorId = list.colorId,
                iconId = list.iconId
            )
        }
}