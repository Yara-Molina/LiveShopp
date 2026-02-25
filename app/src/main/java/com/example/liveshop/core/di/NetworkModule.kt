package com.example.liveshop.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @ProductsRetrofit
    fun provideProductsRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://IP/products")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}