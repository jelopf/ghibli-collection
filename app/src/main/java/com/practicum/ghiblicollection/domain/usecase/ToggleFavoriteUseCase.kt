package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(film: Film) {
        val alreadyFavorite = favoritesRepository.isFavorite(film.id).first()
        if (alreadyFavorite) {
            favoritesRepository.removeFromFavorites(film.id)
        } else {
            favoritesRepository.addToFavorites(film)
        }
    }
}