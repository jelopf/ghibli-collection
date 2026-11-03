package com.practicum.ghiblicollection.ui.profileedit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.usecase.GetProfileUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveAvatarUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveProfileUseCase
import com.practicum.ghiblicollection.domain.usecase.ScheduleLessonReminderUseCase
import com.practicum.ghiblicollection.domain.util.formatTime
import com.practicum.ghiblicollection.domain.util.tryParseTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val scheduleLessonReminderUseCase: ScheduleLessonReminderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileEditUiState>(ProfileEditUiState.Loading)
    val uiState: StateFlow<ProfileEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEditEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProfileEditEvent> = _events.asSharedFlow()

    private var draftName: String = ""
    private var draftAvatarPath: String = ""
    private var draftResumeUrl: String = ""
    private var draftLessonTime: String = ""
    private var draftLessonTimeText: String = ""
    private var draftLessonTimeError: String? = null
    private var draftShowTimePicker: Boolean = false

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profile = getProfileUseCase().first()
            draftName = profile.fullName
            draftAvatarPath = profile.avatarPath
            draftResumeUrl = profile.resumeUrl
            draftLessonTime = profile.lessonTime
            draftLessonTimeText = profile.lessonTime
            draftLessonTimeError = null
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
                resumeUrl = draftResumeUrl,
                lessonTimeText = draftLessonTimeText,
                lessonTimeError = draftLessonTimeError,
                showTimePicker = draftShowTimePicker
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

    fun onLessonTimeTextChange(value: String) {
        draftLessonTimeText = value
        val parsed = tryParseTime(value)
        draftLessonTimeError = when {
            value.isBlank() -> null
            parsed == null -> "Формат: HH:mm"
            else -> null
        }
        draftLessonTime = parsed?.let { formatTime(it) } ?: ""
        emitState()
    }

    fun onTimePickerOpen() {
        draftShowTimePicker = true
        emitState()
    }

    fun onTimePickerDismiss() {
        draftShowTimePicker = false
        emitState()
    }

    fun onTimeConfirmed(hour: Int, minute: Int) {
        val time = LocalTime.of(hour, minute)
        draftLessonTimeText = formatTime(time)
        draftLessonTime = formatTime(time)
        draftLessonTimeError = null
        draftShowTimePicker = false
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
        if (draftLessonTimeText.isNotBlank() && draftLessonTimeError != null) return

        viewModelScope.launch {
            saveProfileUseCase(
                UserProfile(
                    fullName = draftName.trim(),
                    avatarPath = draftAvatarPath,
                    resumeUrl = draftResumeUrl.trim(),
                    lessonTime = draftLessonTime
                )
            )

            if (draftLessonTime.isNotBlank()) {
                _events.emit(ProfileEditEvent.RequestNotificationPermission)
                scheduleLessonReminderUseCase(
                    time = draftLessonTime,
                    name = draftName.trim()
                )
            }

            _events.emit(ProfileEditEvent.NavigateBack)
        }
    }

    fun onCameraPermissionDenied() {
        viewModelScope.launch {
            _events.emit(ProfileEditEvent.ShowCameraPermissionDenied)
        }
    }
}