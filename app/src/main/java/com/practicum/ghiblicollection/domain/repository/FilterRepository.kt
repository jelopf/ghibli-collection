package com.practicum.ghiblicollection.domain.repository

import com.practicum.ghiblicollection.domain.model.FilmFilter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    val filter: Flow<FilmFilter>
    suspend fun saveFilter(filter: FilmFilter)
    suspend fun clearFilter()
}