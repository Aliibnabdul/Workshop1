package com.example.homeworkAA.data

import androidx.paging.PagingSource
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.db.MoviesDatabase
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 20

class MoviesPagingSource(
    private val networkInterface: NetworkInterface,
    private val query: String,
    private val moviesDatabase: MoviesDatabase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try {
            val response = networkInterface.getMoviesListResponse(query, position)
            val list = response.results.map { jsonMovie ->
                Movie(
                    id = jsonMovie.id,
                    title = jsonMovie.title,
                    overview = jsonMovie.overview,
                    posterUrl = "https://image.tmdb.org/t/p/w300${jsonMovie.posterPath}",
                    backdropUrl = "https://image.tmdb.org/t/p/w780${jsonMovie.backdropPath}",
                    ratings = jsonMovie.voteAverage / 2F,
                    numberOfRatings = jsonMovie.voteCount,
                    minimumAge = if (jsonMovie.adult) 16 else 13,
                    runtime = 0,
                    genres = listOf(),
                    actors = listOf()
                )
            }

            val fullList = getMoviesListWithDetails(list)

            moviesDatabase.insertAll(fullList)

            val nextKey = if (fullList.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            return LoadResult.Page(
                data = fullList,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private suspend fun getMoviesListWithDetails(list: List<Movie>): List<Movie> {
        list.forEach {
            val response = networkInterface.getMovieDetailsResponse(it.id)
            it.runtime = response.runtime ?: 0
            it.genres = response.genres
        }
        return list
    }
}