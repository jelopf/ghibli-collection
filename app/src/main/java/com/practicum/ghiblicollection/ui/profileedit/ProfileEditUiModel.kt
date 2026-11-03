package com.practicum.ghiblicollection.ui.profileedit

data class ProfileEditUiModel(
    val fullName: String,
    val avatarPath: String,
    val hasAvatar: Boolean,
    val avatarPlaceholder: String,
    val resumeUrl: String,
    val lessonTime: String = "",
    val lessonTimeText: String = "",
    val lessonTimeError: String? = null,
    val showTimePicker: Boolean = false,
    val pickerInitialHour: Int = 12,
    val pickerInitialMinute: Int = 0,
    val isSaving: Boolean = false
) {
    val canSave: Boolean
        get() = lessonTimeText.isBlank() || lessonTimeError == null
}