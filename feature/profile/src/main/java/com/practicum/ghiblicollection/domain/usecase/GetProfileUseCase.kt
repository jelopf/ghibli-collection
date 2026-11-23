package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repo: ProfileRepository
) {
    operator fun invoke(): Flow<UserProfile> = repo.profile
}