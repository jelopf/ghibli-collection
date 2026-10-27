package com.practicum.ghiblicollection.ui.profileedit

sealed interface ProfileEditEvent {
    data object NavigateBack : ProfileEditEvent
    data object ShowCameraPermissionDenied : ProfileEditEvent
}