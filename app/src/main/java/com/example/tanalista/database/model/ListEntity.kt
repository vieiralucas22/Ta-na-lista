package com.example.tanalista.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "List")
data class ListEntity(
    @ColumnInfo
    val name : String
) : BaseEntity()
