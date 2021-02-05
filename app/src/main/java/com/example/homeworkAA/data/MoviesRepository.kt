package com.example.homeworkAA.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.homeworkAA.data.db.MoviesDatabase
import com.example.homeworkAA.data.db.entities.ActorEntity
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.domain.Movie
import com.example.homeworkAA.data.network.NetworkInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val NETWORK_PAGE_SIZE = 8

class MoviesRepository(
    private val networkInterface: NetworkInterface,
    private val database: MoviesDatabase
) {
    @ExperimentalPagingApi
    fun getSearchResultStream(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                // С этим еще надо поиграться:
                pageSize = 4, // Должно быть в несколько раз больше количества видимых элементов на экране
                prefetchDistance = NETWORK_PAGE_SIZE, // Расстояние предварительной выборки, которое определяет, как далеко от края загруженного содержимого должен быть доступ, чтобы вызвать дальнейшую загрузку. Обычно следует установить в несколько раз большее количество видимых элементов на экране.
                enablePlaceholders = false,
//                initialLoadSize = NETWORK_PAGE_SIZE, // Определяет запрашиваемый размер загрузки для начальной загрузки из [Источника подкачки], обычно больше, чем [Размер страницы], поэтому при первой загрузке данных загружается достаточно большой диапазон содержимого, чтобы охватить небольшие свитки.
//                jumpThreshold = NETWORK_PAGE_SIZE // Определяет порог для количества элементов, прокручиваемых за пределы загруженных элементов, прежде чем Подкачка должна отказаться от постепенной загрузки страниц и вместо этого перейти к позиции пользователя, вызвав ОБНОВЛЕНИЕ через invalidate.
            ),
            remoteMediator = MoviesRemoteMediator(networkInterface, database),
            pagingSourceFactory = { database.moviesDao().getMoviesPagingSource() }
        ).flow
    }

    suspend fun getMovieWithActors(id: Long): Movie = withContext(Dispatchers.IO) {

        try {
            val actors =
                networkInterface.getCastResponse(id).cast.filterNot { it.profilePath == null }
            val actorsEntityList = actors.map { actor -> ActorEntity.fromDto(actor, id) }
            database.actorsDao().insertActors(actorsEntityList)
        } catch (e: Exception) {
        }
        val movieWithActors = database.moviesDao().getMovieWithActors(id)
        return@withContext Movie.fromMovieWithActors(movieWithActors)
    }
}