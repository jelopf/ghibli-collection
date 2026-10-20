package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.domain.repository.FavoritesRepository
import com.practicum.ghiblicollection.domain.repository.FilmRepository
import com.practicum.ghiblicollection.domain.repository.FilterRepository
import com.practicum.ghiblicollection.domain.usecase.ClearFilterUseCase
import com.practicum.ghiblicollection.domain.usecase.FilterFilmsUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFavoritesUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilmByIdUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilmsUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilterUseCase
import com.practicum.ghiblicollection.domain.usecase.IsFavoriteUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveFilterUseCase
import com.practicum.ghiblicollection.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetFilmsUseCase(repository: FilmRepository): GetFilmsUseCase =
        GetFilmsUseCase(repository)

    @Provides
    fun provideGetFilmByIdUseCase(repository: FilmRepository): GetFilmByIdUseCase =
        GetFilmByIdUseCase(repository)

    @Provides
    fun provideGetFilterUseCase(repo: FilterRepository): GetFilterUseCase =
        GetFilterUseCase(repo)

    @Provides
    fun provideSaveFilterUseCase(repo: FilterRepository): SaveFilterUseCase =
        SaveFilterUseCase(repo)

    @Provides
    fun provideClearFilterUseCase(repo: FilterRepository): ClearFilterUseCase =
        ClearFilterUseCase(repo)

    @Provides
    fun provideFilterFilmsUseCase(): FilterFilmsUseCase =
        FilterFilmsUseCase()

    @Provides
    fun provideGetFavoritesUseCase(repo: FavoritesRepository): GetFavoritesUseCase =
        GetFavoritesUseCase(repo)

    @Provides
    fun provideIsFavoriteUseCase(repo: FavoritesRepository): IsFavoriteUseCase =
        IsFavoriteUseCase(repo)

    @Provides
    fun provideToggleFavoriteUseCase(repo: FavoritesRepository): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(repo)
}