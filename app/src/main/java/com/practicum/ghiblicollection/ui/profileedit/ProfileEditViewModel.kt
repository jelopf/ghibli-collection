package com.practicum.ghiblicollection.ui.profileedit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.usecase.GetProfileUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveAvatarUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileEditUiState>(ProfileEditUiState.Loading)
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEditEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProfileEditEvent> = _events.asSharedFlow()

    private var draftName: String = ""
    private var draftAvatarPath: String = ""
    private var draftResumeUrl: String = ""

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profile = getProfileUseCase().first()
            draftName = profile.fullName
            draftAvatarPath = profile.avatarPath
            draftResumeUrl = profile.resumeUrl
            emitState()
        }
    }

    private fun emitState() {
        _uiState.value = ProfileEditUiState.Success(
            ProfileEditUiModel(
                fullName = draftName,
                avatarPath = draftAvatarPath,
                hasAvatar = draftAvatarPath.isNotBlank(),
                avatarPlaceholder = if (draftName.isBlank()) "?" else draftName.first().uppercase(),
                resumeUrl = draftResumeUrl
            )
        )
    }

    fun onNameChange(value: String) {
        draftName = value
        emitState()
    }

    fun onResumeUrlChange(value: String) {
        draftResumeUrl = value
        emitState()
    }

    fun onAvatarPicked(uri: Uri) {
        viewModelScope.launch {
            try {
                val savedPath = saveAvatarUseCase(uri.toString())
                draftAvatarPath = savedPath
                emitState()
            } catch (e: Exception) {
            }
        }
    }

    fun onDoneClick() {
        viewModelScope.launch {
            saveProfileUseCase(
                UserProfile(
                    fullName = draftName.trim(),
                    avatarPath = draftAvatarPath,
                    resumeUrl = draftResumeUrl.trim()
                )
            )
            _events.emit(ProfileEditEvent.NavigateBack)
        }
    }

    fun onCameraPermissionDenied() {
        viewModelScope.launch {
            _events.emit(ProfileEditEvent.ShowCameraPermissionDenied)
        }
    }
}