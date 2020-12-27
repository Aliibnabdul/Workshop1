package com.example.homeworkAA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.homeworkAA.adapter.castList.CastListAdapter
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesDetailsBinding
import com.example.homeworkAA.extensions.movieBundle

class FragmentMoviesDetails : Fragment() {
    private lateinit var binding: FragmentMoviesDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie: Movie = arguments.movieBundle
        initViews(movie)
    }

    private fun initViews(movie: Movie) {
        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            Glide.with(binding.root.context)
                .load(movie.backdrop)
                .into(binding.ivBackImage)
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie.minimumAge)
            tvGenre.text = movie.genres.joinToString(separator = ", ") { it.name }
            ratingBar.rating = movie.ratings
            tvReviews.text = resources.getString(R.string.movie_reviews, movie.numberOfRatings)
            tvName.text = movie.title
            tvDescription.text = movie.overview
            tvCast.visibility = if (movie.actors.isEmpty()) {
                View.GONE
            } else {
                rvCast.adapter = CastListAdapter(movie.actors)
                View.VISIBLE
            }
        }
    }

    companion object {
        fun newInstance(movie: Movie): FragmentMoviesDetails {
            return FragmentMoviesDetails().apply {
                arguments = Bundle().also {
                    it.movieBundle = movie
                }
            }
        }
    }
}