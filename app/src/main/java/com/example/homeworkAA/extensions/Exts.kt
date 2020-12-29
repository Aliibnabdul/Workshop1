package com.example.homeworkAA.extensions

import android.os.Bundle
import com.example.homeworkAA.data.models.Movie
import java.lang.IllegalArgumentException

private const val KEY_MOVIE_OBJECT = "KEY_MOVIE_OBJECT"

var Bundle?.movieBundle: Movie
    get() {
        return this?.getParcelable(KEY_MOVIE_OBJECT) ?: throw IllegalArgumentException("No movie in bundle")
    }
    set(value) {
        this?.putParcelable(KEY_MOVIE_OBJECT, value)
    }