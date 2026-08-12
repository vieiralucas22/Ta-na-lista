package com.example.tanalista.model.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.tanalista.constants.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.LIST_TABLE_NAME)
data class ListEntity(

    @ColumnInfo
    val name: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val colorId: Int,
    @ColumnInfo
    val iconId: Int

) : BaseEntity()
