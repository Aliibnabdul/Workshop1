package com.example.homeworkAA.data.domain

import com.example.homeworkAA.data.db.entities.ActorEntity

data class Actor(
    val actorId: Int,
    val name: String,
    var profilePath: String
) {
    companion object {
        fun fromEntity(actorEntity: ActorEntity): Actor {
            return Actor(
                actorId = actorEntity.actorId,
                name = actorEntity.name,
                profilePath = actorEntity.profilePath
            )
        }
    }
}
