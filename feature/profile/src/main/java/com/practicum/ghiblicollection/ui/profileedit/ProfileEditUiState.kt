package com.practicum.ghiblicollection.ui.profileedit

sealed interface ProfileEditUiState {
    data object Loading : ProfileEditUiState
    data class Success(val profile: ProfileEditUiModel) : ProfileEditUiState
}