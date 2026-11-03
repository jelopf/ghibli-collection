package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmByIdUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(id: String): Film? = repository.getFilmById(id)
}