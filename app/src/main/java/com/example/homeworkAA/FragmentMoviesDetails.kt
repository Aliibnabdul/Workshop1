package com.example.homeworkAA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.example.homeworkAA.adapter.castList.CastListAdapter
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesDetailsBinding

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
        val movie: Movie? = arguments?.getParcelable(KEY_MOVIE_OBJECT)
        movie?.let { initView(it) }
    }

    private fun initView(movie: Movie){
        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            ivBackImage.load(movie.detailsBackImage)
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie.ageLimit)
            tvGenre.text = movie.genre.joinToString(separator = ", ")
            ratingBar.rating = movie.rating
            tvReviews.text = resources.getString(R.string.movie_reviews, movie.reviews)
            tvName.text = movie.name
            tvDescription.text = movie.description
            rvCast.adapter = CastListAdapter(movie.actors)
        }
    }

    companion object {
        private const val KEY_MOVIE_OBJECT = "KEY_MOVIE_OBJECT"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            return FragmentMoviesDetails().apply {
                arguments = bundleOf(
                    KEY_MOVIE_OBJECT to movie
                )
            }
        }
    }
}