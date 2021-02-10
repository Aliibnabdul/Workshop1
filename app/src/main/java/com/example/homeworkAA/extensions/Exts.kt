package com.example.homeworkAA.extensions

import android.os.Bundle

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

var Bundle?.movieIdBundle: Long
    get() {
        return this?.getLong(KEY_MOVIE_ID) ?: throw IllegalArgumentException("No movie in bundle")
    }
    set(value) {
        this?.putLong(KEY_MOVIE_ID, value)
    }
