package com.example.homeworkAA.ui.moviesList

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MoviesListViewHolder(
    private val binding: ViewHolderMovieBinding,
    private val listener: (MovieEntity, Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private val resources: Resources = binding.root.resources

    fun onBind(movie: MovieEntity) {
        Glide.with(itemView.context)
            .load(movie.posterUrl)
            .into(binding.ivBackImage)

        binding.apply {
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie.minimumAge)
            tvGenre.text = movie.genres
            ratingBar.rating = movie.ratings
            tvReviews.text = resources.getString(R.string.movie_reviews, movie.numberOfRatings)
            tvName.text = movie.title
            tvRuntime.text = resources.getString(R.string.movies_list_minutes, movie.runtime)
        }
        setLikedState(false)
        itemView.setOnClickListener {
            listener(movie, absoluteAdapterPosition)
        }
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