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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkInterface {
    @GET("movie/{query}?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US")
    suspend fun getMoviesListResponse(
        @Path("query") query: String,
        @Query("page") page: Int
    ): MoviesListResponse

    @GET("movie/{id}?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US")
    suspend fun getMovieDetailsResponse(@Path("id") id: Long): MovieDetailsResponse

    @GET("movie/{id}/credits?api_key=528872f1b83a56c38aafb7c9fd9dc105&language=en-US")
    suspend fun getCastResponse(@Path("id") id: Long): CastResponse

    companion object {
        @Suppress("EXPERIMENTAL_API_USAGE")
        fun getService(): NetworkInterface {
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

            return retrofit.create(NetworkInterface::class.java)
        }
    }
}