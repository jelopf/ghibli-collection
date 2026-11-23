package com.practicum.ghiblicollection.domain.repository

import com.practicum.ghiblicollection.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<UserProfile>
    suspend fun saveProfile(profile: UserProfile)
    suspend fun saveAvatar(sourceUri: String): String
    suspend fun downloadResume(url: String): Long
}