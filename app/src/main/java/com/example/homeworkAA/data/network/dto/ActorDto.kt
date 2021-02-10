package com.example.homeworkAA.data.network.dto

import com.example.homeworkAA.domain.model.Actor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDto(
    @SerialName("id")
    val actorId: Int,
    val name: String,
    @SerialName("profile_path")
    var profilePath: String?
) {
    fun toDomain(): Actor {
        return Actor(
            actorId,
            name,
            "https://image.tmdb.org/t/p/w185${profilePath}"
        )
    }
}