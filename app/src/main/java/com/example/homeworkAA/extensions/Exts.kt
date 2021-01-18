package com.example.homeworkAA.extensions

import android.os.Bundle

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

var Bundle?.movieIdBundle: Int
    get() {
        return this?.getInt(KEY_MOVIE_ID) ?: throw IllegalArgumentException("No movie in bundle")
    }
    set(value) {
        this?.putInt(KEY_MOVIE_ID, value)
    }