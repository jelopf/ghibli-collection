package com.practicum.ghiblicollection.ui.favorites

import com.practicum.ghiblicollection.domain.model.Film

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val films: List<Film>) : FavoritesUiState
}