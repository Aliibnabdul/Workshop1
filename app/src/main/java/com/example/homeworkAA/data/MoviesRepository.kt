package com.example.homeworkAA.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.data.models.MovieWithActors
import com.example.homeworkAA.db.MoviesDatabase
import com.example.homeworkAA.extensions.isNetworkConnected
import com.example.homeworkAA.extensions.mapToActorsEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val NETWORK_PAGE_SIZE = 8

class MoviesRepository(
    private val networkInterface: NetworkInterface,
    private val database: MoviesDatabase,
    private val context: Context
) {
    @ExperimentalPagingApi
    fun getSearchResultStream(query: String): Flow<PagingData<Movie>> {
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        return Pager(
            config = PagingConfig(
                pageSize = 4, // Должно быть в несколько раз больше количества видимых элементов на экране
                prefetchDistance = NETWORK_PAGE_SIZE, // Расстояние предварительной выборки, которое определяет, как далеко от края загруженного содержимого должен быть доступ, чтобы вызвать дальнейшую загрузку. Обычно следует установить в несколько раз большее количество видимых элементов на экране.
                enablePlaceholders = false,
//                initialLoadSize = NETWORK_PAGE_SIZE, // Определяет запрашиваемый размер загрузки для начальной загрузки из [Источника подкачки], обычно больше, чем [Размер страницы], поэтому при первой загрузке данных загружается достаточно большой диапазон содержимого, чтобы охватить небольшие свитки.
//                jumpThreshold = NETWORK_PAGE_SIZE // Определяет порог для количества элементов, прокручиваемых за пределы загруженных элементов, прежде чем Подкачка должна отказаться от постепенной загрузки страниц и вместо этого перейти к позиции пользователя, вызвав ОБНОВЛЕНИЕ через invalidate.
            ),
            remoteMediator = MoviesRemoteMediator(query, networkInterface, database),
            pagingSourceFactory = { database.moviesDao().getMoviesPagingSource() }
        ).flow
    }

    suspend fun getMovieWithActors(id: Long): MovieWithActors = withContext(Dispatchers.IO) {
        if (isNetworkConnected(context)) {
            val actors =
                networkInterface.getCastResponse(id).cast.filterNot { it.profilePath == null }
                    .mapToActorsEntityList(id)
            database.moviesDao().insertActors(actors)
        }
        return@withContext database.moviesDao().getMovieWithActors(id)
    }
}