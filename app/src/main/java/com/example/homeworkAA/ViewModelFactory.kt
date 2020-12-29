package com.example.homeworkAA

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.ui.movieDetails.MovieDetailsViewModel
import com.example.homeworkAA.ui.moviesList.MoviesListViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(MoviesRepository)
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(MoviesRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}