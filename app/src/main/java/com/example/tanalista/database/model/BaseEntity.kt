package com.example.tanalista.database.model

import androidx.room.PrimaryKey

open class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}