package com.example.homeworkAA.data.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    var runtime: Int,
    var genres: List<Genre>,
    var actors: List<Actor>
)