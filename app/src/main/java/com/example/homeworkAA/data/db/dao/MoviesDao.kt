package com.example.homeworkAA.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.MovieWithActors

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesList: List<MovieEntity>)

    @Query("SELECT * from movies_table")
    fun getMoviesPagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies_table WHERE id == :id")
    suspend fun getMovie(id: Long): MovieEntity

    @Query("DELETE FROM movies_table")
    suspend fun clearList()

    @Transaction
    @Query("SELECT * FROM movies_table WHERE id == :id")
    suspend fun getMovieWithActors(id: Long): MovieWithActors
}