package com.example.homeworkAA.workers

import android.content.Context
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.example.homeworkAA.MoviesConstants.STARTING_PAGE_INDEX
import com.example.homeworkAA.R
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.RemoteKeys
import com.example.homeworkAA.data.network.dto.MovieDetailsRetriever
import com.example.homeworkAA.di.Injection
import com.example.homeworkAA.ui.notifications.NotificationsManager

class RefreshWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    private val networkInterface = Injection.provideNetworkInterface()
    private val moviesDatabase = MoviesDatabase.getInstance(applicationContext)
    private val notificationsManager = NotificationsManager(applicationContext)
    private val movieDetailsRetriever = Injection.provideMovieDetailsRetriever()

    override suspend fun doWork(): Result {
        notificationsManager.showNotification(applicationContext.getString(R.string.refreshWorker_has_started))

        return try {
            val currentQueryValue = "now_playing"

            val resultsList = networkInterface.getMoviesListResponse(
                currentQueryValue,
                STARTING_PAGE_INDEX
            ).results
            val endOfPaginationReached = resultsList.isEmpty()

            val domainMoviesList = resultsList.mapIndexed { index, movieDto ->
                movieDto.toDomain(index + 1)
            }

            val moviesEntityList = domainMoviesList.map { movie ->
                MovieEntity.fromDomain(movie)
            }

            preloadImages(moviesEntityList)

            val dbList = moviesDatabase.moviesDao().getMovies()
            var newOrTopRatedMovieId = moviesEntityList.maxByOrNull { it.ratings }?.id
            var message = applicationContext.getString(R.string.database_updated_tap_to_top_rated_movie)

            if (dbList.isNotEmpty()) {
                moviesEntityList.forEach { movieEntity ->
                    if (dbList.find { movieEntity.id == it.id } == null) {
                        newOrTopRatedMovieId = movieEntity.id
                        message = applicationContext.getString(R.string.database_updated_tap_to_new_movie)
                        return@forEach
                    }
                }
            }

            val fullList = movieDetailsRetriever.getMoviesListWithDetails(moviesEntityList)

            moviesDatabase.withTransaction {
                moviesDatabase.remoteKeysDao().clearRemoteKeys()
                moviesDatabase.moviesDao().clearList()
                val prevKey = null
                val nextKey = if (endOfPaginationReached) null else STARTING_PAGE_INDEX + 1
                val keys = resultsList.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                moviesDatabase.moviesDao().insertAll(fullList)
                moviesDatabase.remoteKeysDao().insertAll(keys)
            }
            notificationsManager.showNotification(
                message,
                newOrTopRatedMovieId
            )
            Result.success()
        } catch (exception: Exception) {
            notificationsManager.showNotification(applicationContext.getString(R.string.refreshWorker_failed))
            Result.failure()
        }
    }

    private fun preloadImages(moviesEntityList: List<MovieEntity>){
        moviesEntityList.forEach { movieEntity ->
            Glide.with(applicationContext)
                .load(movieEntity.posterUrl)
                .preload()

            Glide.with(applicationContext)
                .load(movieEntity.backdropUrl)
                .preload()
        }
    }
}