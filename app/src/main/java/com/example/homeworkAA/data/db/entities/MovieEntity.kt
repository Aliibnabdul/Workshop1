package com.example.homeworkAA.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homeworkAA.data.network.dto.MovieDto

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
        fun fromDto(movieDto: MovieDto, ind: Int): MovieEntity {
            return MovieEntity(
                index = ind,
                id = movieDto.id,
                title = movieDto.title,
                overview = movieDto.overview,
                posterUrl = "https://image.tmdb.org/t/p/w300${movieDto.posterPath}",
                backdropUrl = "https://image.tmdb.org/t/p/w780${movieDto.backdropPath}",
                ratings = movieDto.voteAverage / 2F,
                numberOfRatings = movieDto.voteCount,
                minimumAge = if (movieDto.adult) 16 else 13,
                runtime = 0,
                genres = ""
            )
        }
    }
}