package com.example.homeworkAA.data

import com.example.homeworkAA.BuildConfig
import com.example.homeworkAA.data.models.CastResponse
import com.example.homeworkAA.data.models.MovieDetailsResponse
import com.example.homeworkAA.data.models.MoviesListResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesResponse {
    @GET("movie/now_playing?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US&page=1")
    suspend fun getResponse(): MoviesListResponse
}

interface MovieDetailsResponseInterface {
    @GET("movie/{id}?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US")
    suspend fun getResponse(@Path("id") id: Int): MovieDetailsResponse
}

interface CastResponseInterface {
    @GET("movie/{id}/credits?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US")
    suspend fun getResponse(@Path("id") id: Int): CastResponse
}


object RetrofitObject {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val okHttp = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesResponse: MoviesResponse = retrofit.create()
    val movieDetailsResponse: MovieDetailsResponseInterface = retrofit.create()
    val castResponse: CastResponseInterface = retrofit.create()
}