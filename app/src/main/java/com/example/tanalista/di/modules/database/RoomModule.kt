package com.example.tanalista.di.modules.database

import android.content.Context
import androidx.room.Room
import com.example.tanalista.constants.database.DatabaseConstants
import com.example.tanalista.model.database.ApplicationDatabase
import com.example.tanalista.model.database.dao.ListDao
import com.example.tanalista.model.database.dao.ProductCategoryDao
import com.example.tanalista.model.database.dao.ProductDao
import com.example.tanalista.model.database.dao.ProductListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ApplicationDatabase =
        Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).build()

    @Provides
    fun provideProductDao(db: ApplicationDatabase): ProductDao =
        db.productDao()

    @Provides
    fun provideProductCategoryDao(db: ApplicationDatabase): ProductCategoryDao =
        db.productCategoryDao()

    @Provides
    fun provideListDao(db: ApplicationDatabase): ListDao =
        db.listDao()

    @Provides
    fun provideProductListDao(db: ApplicationDatabase): ProductListDao =
        db.productListDao()

}