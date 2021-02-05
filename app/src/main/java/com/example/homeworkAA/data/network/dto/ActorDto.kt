package com.example.homeworkAA.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    @SerialName("id")
    val actorId: Int,
    val name: String,
    @SerialName("profile_path")
    var profilePath: String?
)