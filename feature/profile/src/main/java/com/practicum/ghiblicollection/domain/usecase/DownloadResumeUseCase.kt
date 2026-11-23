package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import javax.inject.Inject

class DownloadResumeUseCase @Inject constructor(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(url: String): Long = repo.downloadResume(url)
}