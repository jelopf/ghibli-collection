package com.practicum.ghiblicollection.ui.filters

import com.practicum.ghiblicollection.domain.model.FilmFilter

data class FilterUiState(
    val director: String = "",
    val minRating: String = "",
    val yearFrom: String = "",
    val yearTo: String = "",
    val isSaved: Boolean = false
) {
    fun toFilmFilter(): FilmFilter = FilmFilter(
        director = director.trim(),
        minRating = minRating.toIntOrNull() ?: 0,
        yearFrom = yearFrom.toIntOrNull() ?: 0,
        yearTo = yearTo.toIntOrNull() ?: 0
    )

    companion object {
        fun fromFilter(filter: FilmFilter): FilterUiState = FilterUiState(
            director = filter.director,
            minRating = if (filter.minRating > 0) filter.minRating.toString() else "",
            yearFrom = if (filter.yearFrom > 0) filter.yearFrom.toString() else "",
            yearTo = if (filter.yearTo > 0) filter.yearTo.toString() else ""
        )
    }
}