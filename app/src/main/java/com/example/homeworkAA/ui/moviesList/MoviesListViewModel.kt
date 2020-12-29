package com.example.homeworkAA.ui.moviesList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.models.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repository: MoviesRepository): ViewModel() {

    private val mutableMoviesList = MutableLiveData<List<Movie>>()
    val moviesListLiveData: LiveData<List<Movie>> get() = mutableMoviesList

    fun refreshMoviesList(c: Context){
        viewModelScope.launch {
            mutableMoviesList.value = repository.getMoviesList(c)
        }
    }
}