package com.example.homeworkAA.data.network.dto

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
)