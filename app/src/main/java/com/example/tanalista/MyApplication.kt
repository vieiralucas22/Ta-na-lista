package com.example.tanalista

import android.app.Application
import com.example.tanalista.model.database.DatabaseSeeder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application()
{
    @Inject
    lateinit var seeder: DatabaseSeeder

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            seeder.seedDatabase()
        }
    }
}