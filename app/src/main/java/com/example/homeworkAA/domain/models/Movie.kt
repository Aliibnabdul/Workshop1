package com.example.homeworkAA.domain.models

data class Movie(
    val index: Int,
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    var runtime: Int,
    var genres: String,
    val actors: List<Actor>
)
