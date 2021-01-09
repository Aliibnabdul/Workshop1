package com.example.homeworkAA.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository

class DetailsViewModelFactory(private val id: Int) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(MoviesRepository, id)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}