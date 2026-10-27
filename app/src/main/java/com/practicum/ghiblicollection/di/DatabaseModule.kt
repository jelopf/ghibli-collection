package com.practicum.ghiblicollection.di

import android.content.Context
import androidx.room.Room
import com.practicum.ghiblicollection.data.local.database.FavoriteFilmDao
import com.practicum.ghiblicollection.data.local.database.FilmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FilmDatabase = Room.databaseBuilder(
        context,
        FilmDatabase::class.java,
        "ghibli_database"
    ).build()

    @Provides
    @Singleton
    fun provideFavoriteDao(db: FilmDatabase): FavoriteFilmDao = db.favoriteFilmDao()
}