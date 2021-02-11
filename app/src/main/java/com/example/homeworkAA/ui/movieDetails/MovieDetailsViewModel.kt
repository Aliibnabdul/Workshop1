package com.example.homeworkAA.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.domain.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(repository: MoviesRepository, id: Long) : ViewModel() {

    private val mutableMovie = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> = mutableMovie

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mutableMovie.postValue(repository.getMovieWithActors(id))
        }
    }
}