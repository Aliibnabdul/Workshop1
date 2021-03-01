package com.example.homeworkAA.ui.moviesList

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {

    private lateinit var flowPagingData: Flow<PagingData<MovieEntity>>
    val liveDataPaging: LiveData<PagingData<MovieEntity>> get() = flowPagingData.asLiveData()

    private var position = MutableLiveData(0)
    val liveDataPosition: LiveData<Int> get() = position

    init {
        getFlow()
    }

    private fun getFlow() {
        flowPagingData = repository.getSearchResultStream()
            .cachedIn(viewModelScope)
    }

    fun savePosition(pos: Int){
        position.value = pos
    }
}