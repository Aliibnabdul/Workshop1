package com.example.homeworkAA.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    @SerialName("id")
    val actorId: Int,
    val name: String,
    @SerialName("profile_path")
    var profilePath: String? = null
)