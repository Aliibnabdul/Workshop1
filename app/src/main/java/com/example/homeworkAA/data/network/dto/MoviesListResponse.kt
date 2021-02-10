package com.example.homeworkAA.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse(
	val page: Int,
	@SerialName("total_pages")
	val totalPages: Int,
	val results: List<MovieDto>,
	@SerialName("total_results")
	val totalResults: Int
)
