package com.example.homeworkAA.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
	val name: String,
	val id: Int
)

