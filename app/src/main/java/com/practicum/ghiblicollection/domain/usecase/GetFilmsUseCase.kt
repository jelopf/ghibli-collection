package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(): List<Film> = repository.getFilms()
}