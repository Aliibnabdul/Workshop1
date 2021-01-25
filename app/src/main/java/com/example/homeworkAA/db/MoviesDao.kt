package com.example.homeworkAA.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.homeworkAA.data.models.ActorEntity
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.data.models.MovieWithActors

@Dao
interface MoviesDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesList: List<Movie>)

    @Query("SELECT * from movies_table ORDER BY `index` ASC")
    fun getMoviesPagingSource(): PagingSource<Int, Movie>

//    SELECT * FROM movies_table WHERE id LIKE :id
    @Query("SELECT * FROM movies_table WHERE id == :id")
    suspend fun getMovie(id: Long): Movie

    @Query("DELETE FROM movies_table")
    suspend fun clearList()

    @Transaction
    @Query("SELECT * FROM movies_table WHERE id == :id")
    suspend fun getMovieWithActors(id: Long): MovieWithActors

    @Transaction
    @Query("select * from movies_table")
    suspend fun getMoviesWithActors() :List<MovieWithActors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actorsList: List<ActorEntity>)
}