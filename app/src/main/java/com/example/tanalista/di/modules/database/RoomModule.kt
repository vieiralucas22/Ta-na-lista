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
    fun providesRoomDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            DatabaseConstants.DatabaseName
        ).build()
    }

    @Provides
    fun providesProductDao(db: ApplicationDatabase) : ProductDao {
        return db.getProductDao()
    }

    @Provides
    fun providesProductCategoryDao(db: ApplicationDatabase) : ProductCategoryDao {
        return db.getProductCategoryDao()
    }

    @Provides
    fun providesListDao(db: ApplicationDatabase) : ListDao {
        return db.getListDao()
    }

    @Provides
    fun providesProductListDao(db: ApplicationDatabase) : ProductListDao {
        return db.getProductListDao()
    }

}