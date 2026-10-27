package com.practicum.ghiblicollection.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.ghiblicollection.domain.model.Film

@Entity(tableName = "favorite_films")
data class FavoriteFilmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val originalTitle: String,
    val originalTitleRomanised: String,
    val image: String,
    val movieBanner: String,
    val description: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val runningTime: String,
    val rtScore: String
) {
    fun toDomain(): Film = Film(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalTitleRomanised = originalTitleRomanised,
        image = image,
        movieBanner = movieBanner,
        description = description,
        director = director,
        producer = producer,
        releaseDate = releaseDate,
        runningTime = runningTime,
        rtScore = rtScore
    )

    companion object {
        fun fromDomain(film: Film): FavoriteFilmEntity = FavoriteFilmEntity(
            id = film.id,
            title = film.title,
            originalTitle = film.originalTitle,
            originalTitleRomanised = film.originalTitleRomanised,
            image = film.image,
            movieBanner = film.movieBanner,
            description = film.description,
            director = film.director,
            producer = film.producer,
            releaseDate = film.releaseDate,
            runningTime = film.runningTime,
            rtScore = film.rtScore
        )
    }
}