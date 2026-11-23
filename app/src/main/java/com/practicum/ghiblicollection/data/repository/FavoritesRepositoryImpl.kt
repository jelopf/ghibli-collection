package com.practicum.ghiblicollection.data.repository

import com.practicum.ghiblicollection.data.local.database.FavoriteFilmDao
import com.practicum.ghiblicollection.data.local.database.FavoriteFilmEntity
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteFilmDao
) : FavoritesRepository {

    override fun getAllFavorites(): Flow<List<Film>> =
        dao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun isFavorite(id: String): Flow<Boolean> =
        dao.isFavorite(id)

    override suspend fun getFavoriteById(id: String): Film? =
        dao.getFavoriteById(id).first()?.toDomain()

    override suspend fun addToFavorites(film: Film) {
        dao.insertFavorite(FavoriteFilmEntity.fromDomain(film))
    }

    override suspend fun removeFromFavorites(id: String) {
        dao.deleteFavoriteById(id)
    }
}