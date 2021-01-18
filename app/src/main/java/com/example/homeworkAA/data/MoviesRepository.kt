package com.example.homeworkAA.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.db.MoviesDatabase
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val networkService: NetworkInterface,
    private val database: MoviesDatabase
) {
    fun getSearchResultStream(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(networkService, query, database) }
        ).flow
    }

    suspend fun getMovieWithActors(id: Int): Movie {
        val fullList = database.getFullMoviesList()
        val movie = fullList.find { it.id == id }!!
        val castList =
            networkService.getCastResponse(id).cast.filterNot { it.profilePath == null }
        castList.forEach {
            it.profilePath = "https://image.tmdb.org/t/p/w185${it.profilePath}"
        }
        movie.actors = castList
        return movie
    }
}