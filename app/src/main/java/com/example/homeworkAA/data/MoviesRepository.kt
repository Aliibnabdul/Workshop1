package com.example.homeworkAA.data

import com.example.homeworkAA.data.models.Movie

object MoviesRepository {
    private var list: List<Movie> = listOf()

    suspend fun getMoviesList(): List<Movie> {
        list = loadMovies()
        return list
    }

    suspend fun getMoviesListWithDetails(): List<Movie> {
        list = loadMoviesDetails(list)
        return list
    }

    suspend fun getMovieWithActors(id: Int): Movie {
        val movie = list.find { it.id == id }!!
        return loadActors(movie)
    }
}