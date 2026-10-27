package com.practicum.ghiblicollection.domain.model

data class UserProfile(
    val fullName: String = "",
    val avatarPath: String = "",
    val resumeUrl: String = ""
) {
    val isEmpty: Boolean
        get() = fullName.isBlank() && avatarPath.isBlank() && resumeUrl.isBlank()
}