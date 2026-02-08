package com.example.tanalista.model.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.tanalista.constants.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.ListTableName)
data class ListEntity(
    @ColumnInfo
    val name : String
) : BaseEntity()
