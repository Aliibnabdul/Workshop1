package com.example.homeworkAA.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.NetworkInterface
import com.example.homeworkAA.db.MoviesDatabase
import com.example.homeworkAA.ui.movieDetails.DetailsViewModelFactory
import com.example.homeworkAA.ui.moviesList.ListViewModelFactory

object Injection {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var networkInterface: NetworkInterface
    private lateinit var moviesDatabase: MoviesDatabase
    private lateinit var ListViewModelFactory: ListViewModelFactory

    fun setup(context: Context) {
        networkInterface = NetworkInterface.getService()
        moviesDatabase = MoviesDatabase()
        moviesRepository = MoviesRepository(networkInterface, moviesDatabase)
        ListViewModelFactory = ListViewModelFactory(moviesRepository)
    }

    fun provideListViewModelFactory(): ViewModelProvider.Factory {
        return ListViewModelFactory
    }

    fun provideDetailsViewModelFactory(id: Int): ViewModelProvider.Factory {
        return DetailsViewModelFactory(moviesRepository, id)
    }
}