package com.example.homeworkAA.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import com.example.homeworkAA.data.models.Actor
import com.example.homeworkAA.data.models.ActorEntity
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.data.models.ResultsItem

private const val KEY_MOVIE_ID = "KEY_MOVIE_ID"

var Bundle?.movieIdBundle: Long
    get() {
        return this?.getLong(KEY_MOVIE_ID) ?: throw IllegalArgumentException("No movie in bundle")
    }
    set(value) {
        this?.putLong(KEY_MOVIE_ID, value)
    }

fun List<ResultsItem>.mapToMoviesList(ind: Int): List<Movie> {
    return this.mapIndexed { index, jsonMovie ->
        Movie(
            index = index + ind + 1,
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            posterUrl = "https://image.tmdb.org/t/p/w300${jsonMovie.posterPath}",
            backdropUrl = "https://image.tmdb.org/t/p/w780${jsonMovie.backdropPath}",
            ratings = jsonMovie.voteAverage / 2F,
            numberOfRatings = jsonMovie.voteCount,
            minimumAge = if (jsonMovie.adult) 16 else 13,
            runtime = 0,
            genres = ""
//                actors = listOf()
        )
    }
}

fun List<Actor>.mapToActorsEntityList(id: Long): List<ActorEntity> {
    return this.map { actor ->
        ActorEntity(
            movieId = id,
            actorId = actor.actorId,
            name = actor.name,
            profilePath = "https://image.tmdb.org/t/p/w185${actor.profilePath}"
        )
    }
}

fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

