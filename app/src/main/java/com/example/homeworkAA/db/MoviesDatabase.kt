package com.example.homeworkAA.db

import com.example.homeworkAA.data.models.Movie

class MoviesDatabase {

    private var fullList: MutableList<Movie> = mutableListOf()

    fun insertAll(moviesList: List<Movie>) {
        fullList.addAll(moviesList)
    }

    fun getFullMoviesList(): List<Movie> {
        return fullList
    }

    fun clearList() {
        fullList.clear()
    }
}