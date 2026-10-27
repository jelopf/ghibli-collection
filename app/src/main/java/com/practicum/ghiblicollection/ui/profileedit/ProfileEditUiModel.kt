package com.practicum.ghiblicollection.ui.profileedit

data class ProfileEditUiModel(
    val fullName: String,
    val avatarPath: String,
    val hasAvatar: Boolean,
    val avatarPlaceholder: String,
    val resumeUrl: String,
    val isSaving: Boolean = false
)