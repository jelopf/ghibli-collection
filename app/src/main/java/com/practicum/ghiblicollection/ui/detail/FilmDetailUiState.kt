package com.practicum.ghiblicollection.ui.detail

import com.practicum.ghiblicollection.domain.model.Film

sealed interface FilmDetailUiState {
    data object Loading : FilmDetailUiState
    data class Success(val film: Film) : FilmDetailUiState
    data class Error(val message: String) : FilmDetailUiState
}