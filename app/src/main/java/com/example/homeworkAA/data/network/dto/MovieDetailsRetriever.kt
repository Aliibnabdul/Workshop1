package com.example.homeworkAA.data.network.dto

import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.network.MoviesNetworkInterface

class MovieDetailsRetriever(private val networkInterface: MoviesNetworkInterface) {
    suspend fun getMoviesListWithDetails(list: List<MovieEntity>): List<MovieEntity> {
        list.forEach { movie ->
            val response = networkInterface.getMovieDetailsResponse(movie.id)
            movie.runtime = response.runtime ?: 0
            movie.genres = response.genres.joinToString(separator = ", ") { it.name }
        }
        return list
    }
}