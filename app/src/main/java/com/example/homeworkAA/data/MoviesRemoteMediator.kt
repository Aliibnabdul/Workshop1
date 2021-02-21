package com.example.homeworkAA.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.homeworkAA.MoviesConstants.STARTING_PAGE_INDEX
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

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                getRemoteKeyFromLastItem(state)?.nextKey
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            else -> null
        } ?: throw RuntimeException("Problem within transactions, Page cannot be found.")

        try {
            val indexOfLastMovieInDb =
                state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.index ?: 0
            Log.d("INDEX_TAG", "indexOfLastMovieInDb $indexOfLastMovieInDb")

            val resultsList =
                networkInterface.getMoviesListResponse(currentQueryValue, page).results
            val endOfPaginationReached = resultsList.isEmpty()

            val domainMoviesList = resultsList.mapIndexed { index, movieDto ->
                movieDto.toDomain(index + indexOfLastMovieInDb + 1)
            }

            val moviesEntityList = domainMoviesList.map { movie ->
                MovieEntity.fromDomain(movie)
            }
            val fullList = movieDetailsRetriever.getMoviesListWithDetails(moviesEntityList)

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.remoteKeysDao().clearRemoteKeys()
                    moviesDatabase.moviesDao().clearList()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d("LOADTYPE_TAG", "nextKey $nextKey")
                val keys = resultsList.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                moviesDatabase.moviesDao().insertAll(fullList)
                moviesDatabase.remoteKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                moviesDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyFromLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.lastItemOrNull()?.let { movie ->
            moviesDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
        }
    }
}