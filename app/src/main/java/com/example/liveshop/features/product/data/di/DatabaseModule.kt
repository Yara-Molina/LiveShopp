package com.example.liveshop.features.product.data.di

import android.content.Context
import androidx.room.Room
import com.example.liveshop.features.product.data.local.ProductDatabase
import com.example.liveshop.features.product.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "liveshop_database"
        ).build()
    }

    @Provides
    fun provideProductDao(db: ProductDatabase): ProductDao {
        return db.productDao()
    }
}