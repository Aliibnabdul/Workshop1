package com.example.homeworkAA.ui.moviesList

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MovieListViewHolder(private val binding: ViewHolderMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val resources: Resources = binding.root.resources

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
            .load(movie.poster)
            .into(binding.ivBackImage)

        binding.apply {
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie.minimumAge)
            tvGenre.text = movie.genres.joinToString(separator = ", ") { it.name }
            ratingBar.rating = movie.ratings
            tvReviews.text = resources.getString(R.string.movie_reviews, movie.numberOfRatings)
            tvName.text = movie.title
            tvDuration.text = resources.getString(R.string.movies_list_minutes, movie.runtime)
        }
        setLikedState(false)
    }

    private fun setLikedState(liked: Boolean) {
        val heartColor = if (liked) {
            R.color.pink
        } else {
            R.color.white
        }
        binding.ivLikeState.setColorFilter(resources.getColor(heartColor, null))
    }
}