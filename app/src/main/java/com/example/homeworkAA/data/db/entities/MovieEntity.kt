package com.example.homeworkAA.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homeworkAA.domain.models.Movie

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "index")
    val index: Int,
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    var runtime: Int,
    var genres: String
) {
    companion object {
        fun fromDomain(movie: Movie): MovieEntity {
            return MovieEntity(
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
                genres = movie.genres
            )
        }
    }
}