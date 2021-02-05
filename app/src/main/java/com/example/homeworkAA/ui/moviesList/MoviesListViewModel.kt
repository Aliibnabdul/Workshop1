package com.example.homeworkAA.ui.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.db.entities.MovieEntity

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {

    @ExperimentalPagingApi
    val liveDataPagingData: LiveData<PagingData<MovieEntity>>
        get() = repository.getSearchResultStream()
            .cachedIn(viewModelScope).asLiveData()
}