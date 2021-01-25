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
    private lateinit var database: MoviesDatabase
    private lateinit var ListViewModelFactory: ListViewModelFactory

    fun setup(context: Context) {
        networkInterface = NetworkInterface.getService()
        database = MoviesDatabase.getInstance(context)
        moviesRepository = MoviesRepository(networkInterface, database, context)
        ListViewModelFactory = ListViewModelFactory(moviesRepository)
    }

    fun provideListViewModelFactory(): ViewModelProvider.Factory {
        return ListViewModelFactory
    }

    fun provideDetailsViewModelFactory(id: Long): ViewModelProvider.Factory {
        return DetailsViewModelFactory(moviesRepository, id)
    }
}