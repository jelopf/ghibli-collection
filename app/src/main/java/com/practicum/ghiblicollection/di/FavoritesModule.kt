package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.data.repository.FavoritesRepositoryImpl
import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesModule {

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository
}