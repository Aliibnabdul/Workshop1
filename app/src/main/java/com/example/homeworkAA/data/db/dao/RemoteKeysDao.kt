package com.example.homeworkAA.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homeworkAA.data.db.entities.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRemoteKeys(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys")
    suspend fun getRemoteKeys(): RemoteKeys?
}