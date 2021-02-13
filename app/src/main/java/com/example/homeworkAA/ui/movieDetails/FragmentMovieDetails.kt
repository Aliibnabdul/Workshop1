package com.example.homeworkAA.ui.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.domain.models.Movie
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

    private fun initViews(movie: Movie) {
        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            Glide.with(binding.root.context)
                .load(movie.backdropUrl)
                .into(binding.ivBackImage)
            tvAgeLimit.text =
                resources.getString(R.string.age_limit_13plus, movie.minimumAge)
            tvGenre.text = movie.genres
            ratingBar.rating = movie.ratings
            tvReviews.text =
                resources.getString(R.string.movie_reviews, movie.numberOfRatings)
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
        fun newInstance(id: Long): FragmentMovieDetails {
            return FragmentMovieDetails().apply {
                arguments = Bundle().also {
                    it.movieIdBundle = id
                }
            }
        }
    }
}