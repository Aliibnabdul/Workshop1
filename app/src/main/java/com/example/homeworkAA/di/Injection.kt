package com.example.homeworkAA.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.network.NetworkInterface
import com.example.homeworkAA.ui.movieDetails.DetailsViewModelFactory
import com.example.homeworkAA.ui.moviesList.ListViewModelFactory

object Injection {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var networkInterface: NetworkInterface
    private lateinit var database: MoviesDatabase
    private lateinit var listViewModelFactory: ListViewModelFactory

    fun setup(context: Context) {
        networkInterface = NetworkInterface.getService()
        database = MoviesDatabase.getInstance(context)
        moviesRepository = MoviesRepository(networkInterface, database)
        listViewModelFactory = ListViewModelFactory(moviesRepository)
    }

    fun provideNetworkInterface(): NetworkInterface {
        return networkInterface
    }

    fun provideListViewModelFactory(): ViewModelProvider.Factory {
        return listViewModelFactory
    }

    fun provideDetailsViewModelFactory(id: Long): ViewModelProvider.Factory {
        return DetailsViewModelFactory(moviesRepository, id)
    }
}