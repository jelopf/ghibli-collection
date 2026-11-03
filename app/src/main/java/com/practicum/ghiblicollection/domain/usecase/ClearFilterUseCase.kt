package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.repository.FilterRepository
import javax.inject.Inject

class ClearFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke() {
        filterRepository.clearFilter()
    }
}