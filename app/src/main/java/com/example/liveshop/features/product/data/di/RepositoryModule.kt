package com.example.liveshop.features.product.data.di

import com.example.liveshop.features.product.data.repositories.ProductRepositoryImpl
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostsRepository(
        productsRepositoryImpl: ProductRepositoryImpl
    ): ProductsRepository
}