package com.example.homeworkAA.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse(
	val page: Int,
	@SerialName("total_pages")
	val totalPages: Int,
	val results: List<ResultsItem>,
	@SerialName("total_results")
	val totalResults: Int
)

@Serializable
data class ResultsItem(
	val overview: String,
	val title: String,
	@SerialName("poster_path")
	val posterPath: String? = null,
	@SerialName("backdrop_path")
	val backdropPath: String? = null,
	@SerialName("vote_average")
	val voteAverage: Float,
	val id: Long,
	val adult: Boolean,
	@SerialName("vote_count")
	val voteCount: Int,
)

