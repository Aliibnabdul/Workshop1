package com.example.homeworkAA.data

import android.content.Context
import com.example.homeworkAA.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MoviesRepository {
    private lateinit var list: List<Movie>

    suspend fun getMoviesList(c: Context): List<Movie> {
        list = loadMovies(c)
        return list
    }

    fun getMovie(id: Int): Movie =
        list.find { it.id == id }!!
}