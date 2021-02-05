package com.example.homeworkAA.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.homeworkAA.data.db.entities.ActorEntity

@Dao
interface ActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actorsList: List<ActorEntity>)
}