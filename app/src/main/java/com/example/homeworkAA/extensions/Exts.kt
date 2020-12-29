package com.example.homeworkAA.extensions

import android.os.Bundle
import com.example.homeworkAA.data.models.Movie
import java.lang.IllegalArgumentException

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

var Bundle?.movieIdBundle: Int
    get() {
        return this?.getInt(KEY_MOVIE_ID) ?: throw IllegalArgumentException("No movie in bundle")
    }
    set(value) {
        this?.putInt(KEY_MOVIE_ID, value)
    }