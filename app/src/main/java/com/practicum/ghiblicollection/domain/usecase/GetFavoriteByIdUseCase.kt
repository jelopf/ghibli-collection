package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import javax.inject.Inject

class GetFavoriteByIdUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(id: String): Film? =
        favoritesRepository.getFavoriteById(id)
}