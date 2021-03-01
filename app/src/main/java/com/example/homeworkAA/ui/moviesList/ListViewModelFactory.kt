package com.example.homeworkAA.ui.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.homeworkAA.data.MoviesRepository

class ListViewModelFactory(private val moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {

    @ExperimentalPagingApi
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(moviesRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}