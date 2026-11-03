package com.practicum.ghiblicollection.data.repository

import com.practicum.ghiblicollection.data.network.GhibliApi
import com.practicum.ghiblicollection.data.network.mapper.toDomain
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val api: GhibliApi
) : FilmRepository {

    private val filmsCache = MutableStateFlow<List<Film>>(emptyList())

    override suspend fun getFilms(): List<Film> {
        val cached = filmsCache.value
        if (cached.isNotEmpty()) {
            return cached
        }
        val films = api.getFilms().map { it.toDomain() }
        filmsCache.value = films
        return films
    }

    override suspend fun getFilmById(id: String): Film {
        filmsCache.value.firstOrNull { it.id == id }?.let { return it }
        return api.getFilmById(id).toDomain()
    }
}