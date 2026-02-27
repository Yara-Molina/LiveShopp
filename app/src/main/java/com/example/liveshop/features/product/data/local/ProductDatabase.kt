package com.example.liveshop.features.product.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.liveshop.features.product.data.local.dao.ProductDao
import com.example.liveshop.features.product.data.local.entities.ProductEntity


@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase() {


    abstract fun productDao(): ProductDao
}