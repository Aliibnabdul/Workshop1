package com.example.homeworkAA.data.network

import com.example.homeworkAA.BuildConfig
import com.example.homeworkAA.data.network.dto.CastResponse
import com.example.homeworkAA.data.network.dto.MovieDetailsResponse
import com.example.homeworkAA.data.network.dto.MoviesListResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "528872f1b83a56c38aafb7c9fd9dc105"

interface MoviesNetworkInterface {
    @GET("movie/{query}")
    suspend fun getMoviesListResponse(
        @Path("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MoviesListResponse

    @GET("movie/{id}")
    suspend fun getMovieDetailsResponse(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieDetailsResponse

    @GET("movie/{id}/credits")
    suspend fun getCastResponse(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): CastResponse

    companion object {
        @Suppress("EXPERIMENTAL_API_USAGE")
        fun getService(): MoviesNetworkInterface {
            val json = Json {
                ignoreUnknownKeys = true
            }

            val okHttp = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .client(okHttp)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()

            return retrofit.create(MoviesNetworkInterface::class.java)
        }
    }
}