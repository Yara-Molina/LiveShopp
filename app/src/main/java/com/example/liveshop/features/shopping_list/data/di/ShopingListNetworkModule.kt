package com.example.liveshop.features.shopping_list.data.di

import com.example.liveshop.features.shopping_list.data.datasources.remote.api.ShopingListApi
import dagger.Provides
import retrofit2.Retrofit

@Provides
fun provideDashboardApi(retrofit: Retrofit): ShopingListApi =
    retrofit.create(ShopingListApi::class.java)