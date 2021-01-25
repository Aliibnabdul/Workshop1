package com.example.homeworkAA.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homeworkAA.data.models.ActorEntity
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.data.models.MovieWithActors

@Database(entities = [Movie::class, RemoteKeys::class, ActorEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java, "movies.db")
//                .fallbackToDestructiveMigration()
                .build()
    // Note: If you ran and installed the application at the previous step,
    // adding the RemoteKeys entity changes the database schema.
    // This means that you will have to increase the database version to 2 and when building your database,
    // before calling build(), call .fallbackToDestructiveMigration().
    }
}