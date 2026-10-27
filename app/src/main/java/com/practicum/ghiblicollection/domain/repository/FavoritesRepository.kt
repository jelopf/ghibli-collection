package com.practicum.ghiblicollection.domain.repository

import com.practicum.ghiblicollection.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<Film>>
    fun isFavorite(id: String): Flow<Boolean>
    suspend fun getFavoriteById(id: String): Film?
    suspend fun addToFavorites(film: Film)
    suspend fun removeFromFavorites(id: String)
}