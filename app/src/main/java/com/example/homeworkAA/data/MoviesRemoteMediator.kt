package com.example.homeworkAA.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.RemoteKeys
import com.example.homeworkAA.data.network.MoviesNetworkInterface
import com.example.homeworkAA.data.network.dto.MovieDetailsRetriever
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val networkInterface: MoviesNetworkInterface,
    private val moviesDatabase: MoviesDatabase,
    private val movieDetailsRetriever: MovieDetailsRetriever
) : RemoteMediator<Int, MovieEntity>() {

    private val currentQueryValue = "now_playing"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> moviesDatabase.remoteKeysDao().getRemoteKeys()?.nextKey
            }

            val moviesListResponse =
                networkInterface.getMoviesListResponse(currentQueryValue, page)
            val resultsList = moviesListResponse.results

            val endOfPaginationReached = resultsList.isEmpty()

            if (!endOfPaginationReached) {

                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.withTransaction {
                        moviesDatabase.remoteKeysDao().clearRemoteKeys()
                        moviesDatabase.moviesDao().clearList()
                    }
                }

                val lastIndexInDb = moviesDatabase.remoteKeysDao().getRemoteKeys()?.lastDbIndex ?: 0
                val moviesDomainList = resultsList.mapIndexed { index, movieDto ->
                    movieDto.toDomain(index + lastIndexInDb + 1)
                }
                val moviesEntityList = moviesDomainList.map { movie ->
                    MovieEntity.fromDomain(movie)
                }
                val listWithDetails = movieDetailsRetriever.getMoviesListWithDetails(moviesEntityList)

                val nextKey = moviesListResponse.page + 1
                val lastIndexOfNewPage = listWithDetails.last().index

                moviesDatabase.withTransaction {
                    moviesDatabase.moviesDao().insertAll(listWithDetails)
                    moviesDatabase.remoteKeysDao().saveRemoteKeys(
                        RemoteKeys(0, lastDbIndex = lastIndexOfNewPage, nextKey = nextKey)
                    )
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}