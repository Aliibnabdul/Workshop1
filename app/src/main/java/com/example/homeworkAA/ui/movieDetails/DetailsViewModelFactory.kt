package com.example.homeworkAA.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository

class DetailsViewModelFactory(private val repository: MoviesRepository, private val id: Long) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(repository, id)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}