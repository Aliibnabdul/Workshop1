package com.example.homeworkAA.workers

import android.content.Context
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.RemoteKeys
import com.example.homeworkAA.di.Injection
import com.example.homeworkAA.ui.notifications.NotificationsManager

class RefreshWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    private val networkInterface = Injection.provideNetworkInterface()
    private val moviesDatabase = MoviesDatabase.getInstance(applicationContext)
    private val notificationsManager = NotificationsManager(applicationContext)

    override suspend fun doWork(): Result {
        val resources = applicationContext.resources
        notificationsManager.showNotification(resources.getString(R.string.refreshWorker_has_started))

        return try {
            val currentQueryValue = "now_playing"
            val page = 1

            val resultsList = networkInterface.getMoviesListResponse(currentQueryValue, page).results
            val endOfPaginationReached = resultsList.isEmpty()

            val indexOfLastMovieInDb = 0
            val moviesEntityList = resultsList.mapIndexed { index, movieDto ->
                MovieEntity.fromDto(movieDto, index + indexOfLastMovieInDb + 1)
            }

            moviesEntityList.forEach { movieEntity ->
                Glide.with(applicationContext)
                    .load(movieEntity.posterUrl)
                    .preload()

                Glide.with(applicationContext)
                    .load(movieEntity.backdropUrl)
                    .preload()
            }

            val fullList = getMoviesListWithDetails(moviesEntityList)

            moviesDatabase.withTransaction {

                moviesDatabase.remoteKeysDao().clearRemoteKeys()
                moviesDatabase.moviesDao().clearList()

                val prevKey = null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = resultsList.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                moviesDatabase.moviesDao().insertAll(fullList)
                moviesDatabase.remoteKeysDao().insertAll(keys)
            }
            notificationsManager.showNotification(resources.getString(R.string.movies_database_has_been_updated))

            Result.success()
        } catch (exception: Exception) {
            notificationsManager.showNotification(resources.getString(R.string.refreshWorker_failed))

            Result.failure()
        }
    }

    private suspend fun getMoviesListWithDetails(list: List<MovieEntity>): List<MovieEntity> {
        list.forEach { movie ->
            val response = networkInterface.getMovieDetailsResponse(movie.id)
            movie.runtime = response.runtime ?: 0
            movie.genres = response.genres.joinToString(separator = ", ") { it.name }
        }
        return list
    }

}