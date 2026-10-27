package com.practicum.ghiblicollection.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.usecase.DownloadResumeUseCase
import com.practicum.ghiblicollection.domain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val downloadResumeUseCase: DownloadResumeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        observeProfile()
    }

    private fun observeProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { profile ->
                _uiState.value = ProfileUiState.Success(profile.toUiModel())
            }
        }
    }

    fun onResumeClick() {
        val state = _uiState.value as? ProfileUiState.Success ?: return
        val url = state.profile.resumeUrl
        if (url.isBlank()) return

        viewModelScope.launch {
            try {
                downloadResumeUseCase(url)
            } catch (e: Exception) {
            }
        }
    }

    private fun UserProfile.toUiModel() = ProfileUiModel(
        fullName = fullName.ifBlank { "Имя не указано" },
        avatarPath = avatarPath,
        hasAvatar = avatarPath.isNotBlank(),
        avatarPlaceholder = if (fullName.isBlank()) "?" else fullName.first().uppercase(),
        resumeUrl = resumeUrl,
        hasResume = resumeUrl.isNotBlank()
    )
}