package com.example.liveshop.core.data.local

import android.content.Context
import androidx.room.Room
import com.example.liveshop.features.product.data.local.dao.ProductDao
import com.example.liveshop.features.shopping_list.data.local.dao.ShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "liveshop_db"
        ).build()
    }

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    fun provideShoppingListDao(db: AppDatabase): ShoppingListDao = db.shoppingListDao()
}