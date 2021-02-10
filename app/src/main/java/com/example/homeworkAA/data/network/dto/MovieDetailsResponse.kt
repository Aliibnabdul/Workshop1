package com.example.homeworkAA.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val runtime: Int?,
    val genres: List<GenreDto>
)
