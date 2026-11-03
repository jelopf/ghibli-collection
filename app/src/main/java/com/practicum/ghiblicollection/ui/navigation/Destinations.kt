package com.practicum.ghiblicollection.ui.navigation

object Destinations {
    const val FILMS = "films"
    const val FAVORITES = "favorites"
    const val DETAIL = "detail/{filmId}"
    const val FILTERS = "filters"
    const val PROFILE = "profile"
    const val PROFILE_EDIT = "profile_edit"

    fun detailRoute(filmId: String) = "detail/$filmId"
}