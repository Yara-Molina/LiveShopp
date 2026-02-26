
package com.example.liveshop.features.shopping_list.data.di

import com.example.liveshop.features.shopping_list.data.repositories.ShoppingListRepositoryImpl
import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
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
    abstract fun bindListRepository(
        listRepositoryImpl: ShoppingListRepositoryImpl
    ): ListRepository
}
