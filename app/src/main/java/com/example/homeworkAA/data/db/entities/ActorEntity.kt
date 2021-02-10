package com.example.homeworkAA.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homeworkAA.data.network.dto.ActorDto

@Entity
data class ActorEntity(
    val movieId: Long,
    @PrimaryKey val actorId: Int,
    val name: String,
    var profilePath: String
) {
    companion object {
        fun fromDto(actorDto: ActorDto, id: Long): ActorEntity {
            return ActorEntity(
                movieId = id,
                actorId = actorDto.actorId,
                name = actorDto.name,
                profilePath = "https://image.tmdb.org/t/p/w185${actorDto.profilePath}"
            )
        }
    }
}
