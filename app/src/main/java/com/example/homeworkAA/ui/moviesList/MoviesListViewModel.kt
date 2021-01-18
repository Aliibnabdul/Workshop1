package com.example.homeworkAA.ui.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.models.Movie
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult

        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }

        currentQueryValue = queryString
        val newResult: Flow<PagingData<Movie>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}