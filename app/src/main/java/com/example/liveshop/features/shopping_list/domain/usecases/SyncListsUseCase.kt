package com.example.liveshop.features.shopping_list.domain.usecases

import com.example.liveshop.features.shopping_list.domain.repositories.ListRepository
import javax.inject.Inject

class SyncListsUseCase @Inject constructor(
    private val repository: ListRepository
) {
    suspend operator fun invoke() {
        repository.sync_lists()
    }
}