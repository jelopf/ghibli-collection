package com.practicum.ghiblicollection.data.network.mapper

import com.practicum.ghiblicollection.data.network.dto.FilmDto
import com.practicum.ghiblicollection.domain.model.Film

fun FilmDto.toDomain(): Film = Film(
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