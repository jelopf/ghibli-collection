package com.practicum.ghiblicollection.domain.model

data class Film(
    val id: String,
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
)