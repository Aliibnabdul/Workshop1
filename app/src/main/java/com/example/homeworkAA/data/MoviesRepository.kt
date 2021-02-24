package com.example.homeworkAA.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.ActorEntity
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.network.MoviesNetworkInterface
import com.example.homeworkAA.data.network.dto.MovieDetailsRetriever
import com.example.homeworkAA.domain.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val NETWORK_PAGE_SIZE = 8

class MoviesRepository(
    private val networkInterface: MoviesNetworkInterface,
    private val database: MoviesDatabase,
    private val movieDetailsRetriever: MovieDetailsRetriever
) {
    @ExperimentalPagingApi
    fun getSearchResultStream(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
                prefetchDistance = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MoviesRemoteMediator(networkInterface, database, movieDetailsRetriever),
            pagingSourceFactory = { database.moviesDao().getMoviesPagingSource() }
        ).flow
    }

    suspend fun getMovieWithActors(id: Long): Movie = withContext(Dispatchers.IO) {
        try {
            val actors =
                networkInterface.getCastResponse(id).cast.filterNot { it.profilePath == null }
            val actorsEntityList = actors
                .map { actorDto -> actorDto.toDomain() }
                .map { ActorEntity.fromDomain(it, id) }
            database.actorsDao().insertActors(actorsEntityList)
        } catch (e: Exception) {
        }
        val movieWithActors = database.moviesDao().getMovieWithActors(id)
        return@withContext movieWithActors.toDomain()
    }
}