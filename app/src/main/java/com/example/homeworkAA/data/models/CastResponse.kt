package com.example.homeworkAA.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
	val cast: List<Actor>,
)

