package com.example.tanalista.model.database.model

import androidx.room.PrimaryKey

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}