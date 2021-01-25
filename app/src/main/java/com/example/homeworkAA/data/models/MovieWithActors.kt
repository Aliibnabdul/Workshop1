package com.example.homeworkAA.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithActors(
    @Embedded val movie: Movie,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = ActorEntity::class
    )
    val actors: List<ActorEntity>
)