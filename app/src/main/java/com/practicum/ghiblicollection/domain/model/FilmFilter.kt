package com.practicum.ghiblicollection.domain.model

data class FilmFilter(
    val director: String = "",
    val minRating: Int = 0,
    val yearFrom: Int = 0,
    val yearTo: Int = 0
) {
    val isActive: Boolean
        get() = director.isNotBlank() || minRating > 0 || yearFrom > 0 || yearTo > 0

    companion object {
        val EMPTY = FilmFilter()
    }
}