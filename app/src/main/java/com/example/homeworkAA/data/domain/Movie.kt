package com.example.homeworkAA.data.domain

import com.example.homeworkAA.data.db.entities.MovieWithActors

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
) {
    companion object {
        fun fromMovieWithActors(movieWithActors: MovieWithActors): Movie {
            return Movie(
                index = movieWithActors.movie.index,
                id = movieWithActors.movie.id,
                title = movieWithActors.movie.title,
                overview = movieWithActors.movie.overview,
                posterUrl = movieWithActors.movie.posterUrl,
                backdropUrl = movieWithActors.movie.backdropUrl,
                ratings = movieWithActors.movie.ratings,
                numberOfRatings = movieWithActors.movie.numberOfRatings,
                minimumAge = movieWithActors.movie.minimumAge,
                runtime = movieWithActors.movie.runtime,
                genres = movieWithActors.movie.genres,
                actors = movieWithActors.actors.map { Actor.fromEntity(it) }
            )
        }
    }
}
