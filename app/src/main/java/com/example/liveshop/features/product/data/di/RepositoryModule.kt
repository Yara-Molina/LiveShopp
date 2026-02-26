package com.example.liveshop.features.product.data.di

import com.example.liveshop.features.product.data.datasources.remote.api.ProductWSRepository
import com.example.liveshop.features.product.data.repositories.ProductRepositoryImpl
import com.example.liveshop.features.product.data.repositories.ProductWSRepositoryImpl
import com.example.liveshop.features.product.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        productsRepositoryImpl: ProductRepositoryImpl
    ): ProductsRepository // http

    @Binds
    @Singleton
    abstract fun bindProductWSApi(
        productWSApiImpl: ProductWSRepositoryImpl
    ): ProductWSRepository // websocket
}