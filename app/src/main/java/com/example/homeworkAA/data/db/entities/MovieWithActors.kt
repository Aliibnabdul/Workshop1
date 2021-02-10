package com.example.homeworkAA.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.homeworkAA.data.db.entities.ActorEntity

data class MovieWithActors(
    @Embedded val movie: MovieEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = ActorEntity::class
    )
    val actors: List<ActorEntity>
)