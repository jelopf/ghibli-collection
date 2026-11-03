package com.practicum.ghiblicollection.data.network

import com.practicum.ghiblicollection.data.network.dto.FilmDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GhibliApi {

    @GET("films")
    suspend fun getFilms(): List<FilmDto>

    @GET("films/{id}")
    suspend fun getFilmById(@Path("id") id: String): FilmDto
}