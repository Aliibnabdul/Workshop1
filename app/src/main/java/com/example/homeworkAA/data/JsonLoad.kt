package com.example.homeworkAA.data

import com.example.homeworkAA.data.models.Movie

@Suppress("unused")
internal suspend fun loadMovies(): List<Movie> {
    val list = RetrofitObject.moviesResponse.getResponse().results
    return list.map { jsonMovie ->
        @Suppress("unused")
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            posterUrl = "https://image.tmdb.org/t/p/w300${jsonMovie.posterPath}",
            backdropUrl = "https://image.tmdb.org/t/p/w780${jsonMovie.backdropPath}",
            ratings = jsonMovie.voteAverage / 2F,
            numberOfRatings = jsonMovie.voteCount,
            minimumAge = if (jsonMovie.adult) 16 else 13,
            runtime = 0,
            genres = listOf(),
            actors = listOf()
        )
    }
}

internal suspend fun loadMoviesDetails(list: List<Movie>): List<Movie> {
    list.forEach {
        val response = RetrofitObject.movieDetailsResponse.getResponse(it.id)
        it.runtime = response.runtime ?: 0
        it.genres = response.genres
    }
    return list
}

internal suspend fun loadActors(movie: Movie): Movie {
    val castList =
        RetrofitObject.castResponse.getResponse(movie.id).cast.filterNot { it.profilePath == null }
    castList.forEach {
        val imageUrl = it.profilePath
        it.profilePath = "https://image.tmdb.org/t/p/w185$imageUrl"
    }
    movie.actors = castList
    return movie
}
