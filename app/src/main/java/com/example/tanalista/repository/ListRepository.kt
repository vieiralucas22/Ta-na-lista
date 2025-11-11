package com.example.tanalista.repository

import android.content.Context
import com.example.tanalista.database.ApplicationDatabase

class ListRepository (context : Context){

    val listDao = ApplicationDatabase.getDatabase(context).ListDao()

}