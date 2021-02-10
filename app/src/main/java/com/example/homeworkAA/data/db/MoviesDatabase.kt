package com.example.homeworkAA.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homeworkAA.data.db.dao.ActorsDao
import com.example.homeworkAA.data.db.dao.MoviesDao
import com.example.homeworkAA.data.db.dao.RemoteKeysDao
import com.example.homeworkAA.data.db.entities.ActorEntity
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.data.db.entities.RemoteKeys

@Database(
    entities = [MovieEntity::class, RemoteKeys::class, ActorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun actorsDao(): ActorsDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: MoviesDatabase

        fun getInstance(context: Context): MoviesDatabase =
            synchronized(this) {
                if (::INSTANCE.isInitialized) {
                    INSTANCE
                } else {
                    buildDatabase(context).also { INSTANCE = it }
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java, "movies.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}