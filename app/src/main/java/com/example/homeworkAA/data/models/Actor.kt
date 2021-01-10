package com.example.homeworkAA.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    var profilePath: String? = null
)