package com.example.homeworkAA.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActorEntity(
    val movieId: Long,
    @PrimaryKey val actorId: Int,
    val name: String,
    var profilePath: String? = null
)
