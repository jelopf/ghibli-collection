package com.practicum.ghiblicollection.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteFilmDao {

    @Query("SELECT * FROM favorite_films")
    fun getAllFavorites(): Flow<List<FavoriteFilmEntity>>

    @Query("SELECT * FROM favorite_films WHERE id = :id LIMIT 1")
    fun getFavoriteById(id: String): Flow<FavoriteFilmEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(film: FavoriteFilmEntity)

    @Query("DELETE FROM favorite_films WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_films WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}