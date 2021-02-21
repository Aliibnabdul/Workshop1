package com.example.homeworkAA.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkAA.data.MoviesRepository
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.network.MoviesNetworkInterface
import com.example.homeworkAA.data.network.dto.MovieDetailsRetriever
import com.example.homeworkAA.ui.movieDetails.DetailsViewModelFactory
import com.example.homeworkAA.ui.moviesList.ListViewModelFactory

object Injection {
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var networkInterface: MoviesNetworkInterface
    private lateinit var database: MoviesDatabase
    private lateinit var listViewModelFactory: ListViewModelFactory
    private lateinit var movieDetailsRetriever: MovieDetailsRetriever

    fun setup(context: Context) {
        networkInterface = MoviesNetworkInterface.getService()
        database = MoviesDatabase.getInstance(context)
        movieDetailsRetriever = MovieDetailsRetriever(networkInterface)
        moviesRepository = MoviesRepository(networkInterface, database, movieDetailsRetriever)
        listViewModelFactory = ListViewModelFactory(moviesRepository)
    }

    fun provideNetworkInterface(): MoviesNetworkInterface {
        return networkInterface
    }

    fun provideListViewModelFactory(): ViewModelProvider.Factory {
        return listViewModelFactory
    }

    fun provideMovieDetailsRetriever(): MovieDetailsRetriever {
        return movieDetailsRetriever
    }

    fun provideDetailsViewModelFactory(id: Long): ViewModelProvider.Factory {
        return DetailsViewModelFactory(moviesRepository, id)
    }
}