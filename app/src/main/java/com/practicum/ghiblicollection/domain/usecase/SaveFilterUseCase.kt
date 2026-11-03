package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.FilmFilter
import com.practicum.ghiblicollection.domain.repository.FilterRepository
import javax.inject.Inject

class SaveFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filter: FilmFilter) {
        filterRepository.saveFilter(filter)
    }
}