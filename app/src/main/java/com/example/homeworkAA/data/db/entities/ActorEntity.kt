package com.example.homeworkAA.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homeworkAA.domain.models.Actor

@Entity
data class ActorEntity(
    val movieId: Long,
    @PrimaryKey val actorId: Int,
    val name: String,
    var profilePath: String
) {
    fun toDomain(): Actor {
        return Actor(
            actorId = actorId,
            name = name,
            profilePath = profilePath
        )
    }

    companion object {
        fun fromDomain(actor: Actor, movieId: Long): ActorEntity {
            return ActorEntity(
                movieId = movieId,
                actorId = actor.actorId,
                name = actor.name,
                profilePath = actor.profilePath
            )
        }
    }
}
