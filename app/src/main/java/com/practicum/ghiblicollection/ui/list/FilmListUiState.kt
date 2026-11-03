package com.practicum.ghiblicollection.ui.list

import com.practicum.ghiblicollection.domain.model.Film

sealed interface FilmListUiState {
    data object Loading : FilmListUiState
    data class Success(val films: List<Film>) : FilmListUiState
    data class Error(val message: String) : FilmListUiState
}