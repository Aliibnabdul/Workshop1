package com.example.homeworkAA.adapter.moviesList

import android.content.res.ColorStateList
import android.content.res.Resources
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homeworkAA.R
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MovieListViewHolder(private val binding: ViewHolderMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val resources: Resources = binding.root.resources

    fun onBind(movie: Movie) {
        binding.apply {
            ivBackImage.load(movie.backImage)
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie.ageLimit)
            tvGenre.text = movie.genre.joinToString(separator = ", ")
            ratingBar.rating = movie.rating
            tvReviews.text = resources.getString(R.string.movie_reviews, movie.reviews)
            tvName.text = movie.name
            tvDuration.text = resources.getString(R.string.movies_list_minutes, movie.duration)
        }
        setLikedState(movie.likeState)
    }

    private fun setLikedState(liked: Boolean) {
        val heartColor = if (liked)R.color.pink
        else R.color.white
        binding.ivLikeState.setColorFilter(resources.getColor(heartColor, null))
    }
}