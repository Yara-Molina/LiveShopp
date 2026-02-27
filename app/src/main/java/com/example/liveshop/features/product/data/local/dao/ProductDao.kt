package com.example.liveshop.features.product.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.example.liveshop.features.product.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE listId = :listId")
    fun getProductsFlow(listId: String): Flow<List<ProductEntity>>

    @Upsert
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("UPDATE products SET status = :status WHERE id = :productId")
    suspend fun updateProductStatus(productId: String, status: String)


    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: String)


    @Query("DELETE FROM products WHERE listId = :listId")
    suspend fun deleteProductsByList(listId: String)
}