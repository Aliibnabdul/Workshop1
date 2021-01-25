package com.example.homeworkAA.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.db.MoviesDatabase
import com.example.homeworkAA.db.RemoteKeys
import com.example.homeworkAA.extensions.mapToMoviesList
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val query: String,
    private val networkInterface: NetworkInterface,
    private val moviesDatabase: MoviesDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

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

            val resultsList = networkInterface.getMoviesListResponse(query, page).results
            val endOfPaginationReached = resultsList.isEmpty()

            val moviesList = resultsList.mapToMoviesList(indexOfLastMovieInDb)
            val fullList = getMoviesListWithDetails(moviesList)

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDatabase.remoteKeysDao().clearRemoteKeys()
                    moviesDatabase.moviesDao().clearList()
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                moviesDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyFromLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.lastItemOrNull()?.let { movie ->
            moviesDatabase.remoteKeysDao().remoteKeysRepoId(movie.id)
        }
    }

    private suspend fun getMoviesListWithDetails(list: List<Movie>): List<Movie> {
        list.forEach { movie ->
            val response = networkInterface.getMovieDetailsResponse(movie.id)
            movie.runtime = response.runtime ?: 0
            movie.genres = response.genres.joinToString(separator = ", ") { it.name }
        }
        return list
    }
}