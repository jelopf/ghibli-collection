package com.practicum.ghiblicollection.ui.navigation

object Destinations {
    const val FILMS = "films"
    const val FAVORITES = "favorites"
    const val DETAIL = "detail/{filmId}"
    const val FILTERS = "filters"

    fun detailRoute(filmId: String) = "detail/$filmId"
}