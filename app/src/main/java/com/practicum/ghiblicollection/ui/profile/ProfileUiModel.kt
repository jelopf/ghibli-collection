package com.practicum.ghiblicollection.ui.profile

data class ProfileUiModel(
    val fullName: String,
    val avatarPath: String,
    val hasAvatar: Boolean,
    val avatarPlaceholder: String,
    val resumeUrl: String,
    val hasResume: Boolean
)