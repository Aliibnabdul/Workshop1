package com.example.homeworkAA.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.data.models.MovieWithActors
import com.example.homeworkAA.databinding.FragmentMoviesDetailsBinding
import com.example.homeworkAA.di.Injection
import com.example.homeworkAA.extensions.movieIdBundle

class FragmentMovieDetails : Fragment() {
    private lateinit var binding: FragmentMoviesDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        Injection.provideDetailsViewModelFactory(arguments.movieIdBundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner, {
            initViews(it)
        })
    }

    private fun initViews(movieWithActors: MovieWithActors) {
        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            Glide.with(binding.root.context)
                .load(movieWithActors.movie.backdropUrl)
                .into(binding.ivBackImage)
            tvAgeLimit.text =
                resources.getString(R.string.age_limit_13plus, movieWithActors.movie.minimumAge)
            tvGenre.text = movieWithActors.movie.genres
            ratingBar.rating = movieWithActors.movie.ratings
            tvReviews.text =
                resources.getString(R.string.movie_reviews, movieWithActors.movie.numberOfRatings)
            tvName.text = movieWithActors.movie.title
            tvDescription.text = movieWithActors.movie.overview
            tvCast.visibility = if (movieWithActors.actors.isEmpty()) {
                View.GONE
            } else {
                rvCast.adapter = CastListAdapter(movieWithActors.actors)
                View.VISIBLE
            }
        }
    }

    companion object {
        fun newInstance(id: Long): FragmentMovieDetails {
            return FragmentMovieDetails().apply {
                arguments = Bundle().also {
                    it.movieIdBundle = id
                }
            }
        }
    }
}