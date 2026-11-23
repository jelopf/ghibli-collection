package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.model.FilmFilter
import javax.inject.Inject

class FilterFilmsUseCase @Inject constructor() {

    operator fun invoke(films: List<Film>, filter: FilmFilter): List<Film> {
        if (!filter.isActive) return films

        return films.filter { film ->
            val directorMatches = filter.director.isBlank() ||
                    film.director.contains(filter.director, ignoreCase = true)

            val rating = film.rtScore.toIntOrNull() ?: 0
            val ratingMatches = filter.minRating <= 0 || rating >= filter.minRating

            val year = film.releaseDate.toIntOrNull() ?: 0
            val yearFromMatches = filter.yearFrom <= 0 || year >= filter.yearFrom
            val yearToMatches = filter.yearTo <= 0 || year <= filter.yearTo

            directorMatches && ratingMatches && yearFromMatches && yearToMatches
        }
    }
}