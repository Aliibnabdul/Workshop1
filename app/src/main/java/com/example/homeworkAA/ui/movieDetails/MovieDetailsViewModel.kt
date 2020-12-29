package com.example.homeworkAA.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.models.Movie

class MovieDetailsViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val mutableMovie = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = mutableMovie

    fun refreshMovie(id: Int) {
        mutableMovie.value = repository.getMovie(id)
    }
}