package com.example.liveshop.features.product.data.di


import com.example.liveshop.features.product.data.datasources.remote.api.ProductHTTPApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductNetworkModule {
    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductHTTPApi {
        return retrofit.create(ProductHTTPApi::class.java)
    }
}