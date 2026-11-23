package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(id: String): Flow<Boolean> = favoritesRepository.isFavorite(id)
}