package com.example.homeworkAA.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.models.Movie

class MovieDetailsViewModel(repository: MoviesRepository, id: Int) : ViewModel() {

    val movieLiveData: LiveData<Movie> = MutableLiveData(repository.getMovie(id))

}