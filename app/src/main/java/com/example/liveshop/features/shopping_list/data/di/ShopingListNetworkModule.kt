package com.example.liveshop.features.shopping_list.data.di

import com.example.liveshop.features.product.data.datasources.remote.api.ProductHTTPApi
import com.example.liveshop.features.shopping_list.data.datasources.remote.api.SharedListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopingListNetworkModule {
    @Provides
    @Singleton
    fun provideListApi(retrofit: Retrofit): SharedListApi {
        return retrofit.create(SharedListApi::class.java)
    }
}