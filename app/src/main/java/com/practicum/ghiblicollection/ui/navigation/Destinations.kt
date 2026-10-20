package com.practicum.ghiblicollection.ui.navigation

object Destinations {
    const val FILMS = "films"
    const val HOME = "home"
    const val DETAIL = "detail/{filmId}"
    const val FILTERS = "filters"

    fun detailRoute(filmId: String) = "detail/$filmId"
}