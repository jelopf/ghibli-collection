package com.practicum.ghiblicollection.ui.profile

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val profile: ProfileUiModel) : ProfileUiState
}