package com.example.liveshop.features.shopping_list.data.repositories

import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSharedListRepository(
        impl: ShopingListRepositorylmpl
    ): ListRepository
}