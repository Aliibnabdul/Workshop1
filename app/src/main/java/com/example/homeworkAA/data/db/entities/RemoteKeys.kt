package com.example.homeworkAA.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val keysId: Long,
    val lastDbIndex: Int?,
    val nextKey: Int?
)
