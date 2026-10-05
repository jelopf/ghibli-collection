package com.practicum.ghiblicollection.domain.repository

import com.practicum.ghiblicollection.domain.model.Film

interface FilmRepository {
    suspend fun getFilms(): List<Film>
    suspend fun getFilmById(id: String): Film?
}