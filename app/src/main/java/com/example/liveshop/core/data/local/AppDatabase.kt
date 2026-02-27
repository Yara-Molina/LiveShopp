package com.example.liveshop.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.liveshop.features.product.data.local.dao.ProductDao
import com.example.liveshop.features.product.data.local.entities.ProductEntity
import com.example.liveshop.features.shopping_list.data.local.dao.ShoppingListDao
import com.example.liveshop.features.shopping_list.data.local.entities.ShoppingListEntity

@Database(
    entities = [
        ProductEntity::class,
        ShoppingListEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun shoppingListDao(): ShoppingListDao
}