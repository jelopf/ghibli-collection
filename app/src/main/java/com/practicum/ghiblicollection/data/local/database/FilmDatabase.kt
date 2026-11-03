package com.practicum.ghiblicollection.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteFilmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun favoriteFilmDao(): FavoriteFilmDao
}