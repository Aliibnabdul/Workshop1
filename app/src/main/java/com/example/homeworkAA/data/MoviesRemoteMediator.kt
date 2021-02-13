package com.example.homeworkAA.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.RemoteKeys
import com.example.homeworkAA.data.network.NetworkInterface
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val networkInterface: NetworkInterface,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, MovieEntity>() {

    private val currentQueryValue = "now_playing"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
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

            val resultsList =
                networkInterface.getMoviesListResponse(currentQueryValue, page).results
            val endOfPaginationReached = resultsList.isEmpty()

            val moviesEntityList = resultsList.mapIndexed { index, movieDto ->
                MovieEntity.fromDto(movieDto, index + indexOfLastMovieInDb + 1)
            }
            val fullList = getMoviesListWithDetails(moviesEntityList)

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.remoteKeysDao().clearRemoteKeys()
                    moviesDatabase.moviesDao().clearList()
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
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

    private suspend fun getMoviesListWithDetails(list: List<MovieEntity>): List<MovieEntity> {
        list.forEach { movie ->
            val response = networkInterface.getMovieDetailsResponse(movie.id)
            movie.runtime = response.runtime ?: 0
            movie.genres = response.genres.joinToString(separator = ", ") { it.name }
        }
        return list
    }
}