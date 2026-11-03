package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Film>> = favoritesRepository.getAllFavorites()
}