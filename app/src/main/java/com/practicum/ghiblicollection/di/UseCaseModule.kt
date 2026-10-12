package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.domain.repository.FilmRepository
import com.practicum.ghiblicollection.domain.usecase.GetFilmByIdUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilmsUseCase
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
}