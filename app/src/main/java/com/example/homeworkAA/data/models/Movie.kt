package com.example.homeworkAA.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class Movie(
//    val backImage: String,
//    val detailsBackImage: String,
//    val ageLimit: Int,
//    val likeState: Boolean,
//    val genre: List<String>,
//    val rating: Float,
//    val reviews: Int,
//    val name: String,
//    val duration: Int,
//    val description: String,
//    val actors: List<Actor>
//    ) : Parcelable

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor>
) : Parcelable