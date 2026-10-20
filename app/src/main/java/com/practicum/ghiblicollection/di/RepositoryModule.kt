package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.data.repository.FilmRepositoryImpl
import com.practicum.ghiblicollection.domain.repository.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFilmRepository(
        impl: FilmRepositoryImpl
    ): FilmRepository
}