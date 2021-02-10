package com.example.homeworkAA.data.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.homeworkAA.domain.model.Movie

data class MovieWithActors(
    @Embedded val movie: MovieEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = ActorEntity::class
    )
    val actors: List<ActorEntity>
) {
    fun toDomain(): Movie {
        return Movie(
            index = movie.index,
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterUrl = movie.posterUrl,
            backdropUrl = movie.backdropUrl,
            ratings = movie.ratings,
            numberOfRatings = movie.numberOfRatings,
            minimumAge = movie.minimumAge,
            runtime = movie.runtime,
            genres = movie.genres,
            actors = actors.map(ActorEntity::toDomain)
        )
    }
}