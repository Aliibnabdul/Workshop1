package com.example.homeworkAA.data.network.dto

import com.example.homeworkAA.data.db.entities.ActorEntity
import com.example.homeworkAA.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val overview: String,
    val title: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("vote_average")
    val voteAverage: Float,
    val id: Long,
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int,
) {
    fun toDomain(ind: Int): Movie {
        return Movie(
            index = ind,
            id = id,
            title = title,
            overview = overview,
            posterUrl = "https://image.tmdb.org/t/p/w300$posterPath",
            backdropUrl = "https://image.tmdb.org/t/p/w780$backdropPath",
            ratings = voteAverage / 2F,
            numberOfRatings = voteCount,
            minimumAge = if (adult) 16 else 13,
            runtime = 0,
            genres = "",
            actors = listOf()
        )
    }
}