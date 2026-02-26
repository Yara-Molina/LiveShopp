package com.example.liveshop.features.shopping_list.data.di

import com.example.liveshop.features.shopping_list.data.repositories.ShopingListRepositoryImpl
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedListRepositoryModule {

    @Binds
    abstract fun bindSharedListRepository(
        impl: ShopingListRepositoryImpl
    ): ListRepository
}