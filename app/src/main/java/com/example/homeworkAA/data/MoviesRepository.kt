package com.example.homeworkAA.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.ActorEntity
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.network.NetworkInterface
import com.example.homeworkAA.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val NETWORK_PAGE_SIZE = 8

class MoviesRepository(
    private val networkInterface: NetworkInterface,
    private val database: MoviesDatabase
) {
    @ExperimentalPagingApi
    fun getSearchResultStream(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                prefetchDistance = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = MoviesRemoteMediator(networkInterface, database),
            pagingSourceFactory = { database.moviesDao().getMoviesPagingSource() }
        ).flow
    }

    suspend fun getMovieWithActors(id: Long): Movie = withContext(Dispatchers.IO) {

        try {
            val actors = networkInterface
                .getCastResponse(id).cast
                .filterNot { it.profilePath == null }

            val actorsEntityList = actors
                .map { actor -> actor.toDomain() }
                .map { ActorEntity.from(it, id) }

            database.actorsDao().insertActors(actorsEntityList)
        } catch (e: Exception) {
        }

        val movieWithActors = database.moviesDao().getMovieWithActors(id)

        return@withContext movieWithActors.toDomain()
    }
}