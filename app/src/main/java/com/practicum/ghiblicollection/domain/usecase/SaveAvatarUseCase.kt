package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveAvatarUseCase @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(sourceUri: String): String = repo.saveAvatar(sourceUri)
}