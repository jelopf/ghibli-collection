package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile) {
        repo.saveProfile(profile)
    }
}