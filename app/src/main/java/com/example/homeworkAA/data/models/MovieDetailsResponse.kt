package com.example.homeworkAA.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val runtime: Int? = null,
    val genres: List<Genre>
)
