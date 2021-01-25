package com.example.homeworkAA.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "index")
    val index: Int,
    @ColumnInfo
    val id: Long,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val overview: String,
    @ColumnInfo
    val posterUrl: String,
    @ColumnInfo
    val backdropUrl: String,
    @ColumnInfo
    val ratings: Float,
    @ColumnInfo
    val numberOfRatings: Int,
    @ColumnInfo
    val minimumAge: Int,
    @ColumnInfo
    var runtime: Int,
    @ColumnInfo
    var genres: String
)