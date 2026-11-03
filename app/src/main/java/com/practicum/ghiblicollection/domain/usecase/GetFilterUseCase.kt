package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.FilmFilter
import com.practicum.ghiblicollection.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    operator fun invoke(): Flow<FilmFilter> = filterRepository.filter
}