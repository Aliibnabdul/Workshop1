package com.example.homeworkAA

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homeworkAA.adapter.castList.CastListAdapter
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : Fragment() {
    private lateinit var binding: FragmentMoviesDetailsBinding
    private var castRecycler: RecyclerView? = null
    private lateinit var castListAdapter: CastListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val movie: Movie? = arguments?.getParcelable(KEY_MOVIE_OBJECT)

        binding.apply {
            ivBackImage.load(movie?.detailsBAckImage)
            tvAgeLimit.text = resources.getString(R.string.age_limit_13plus, movie?.ageLimit)
            tvGenre.text = movie?.genre?.joinToString(separator = ", ") ?: ""
            ratingBar.rating = movie?.rating ?: 0F
            tvReviews.text = resources.getString(R.string.movie_reviews, movie?.reviews)
            tvName.text = movie?.name ?: ""
            tvDescription.text = movie?.description
        }

        val actorsList = movie?.actors
        if (actorsList != null) {
            castListAdapter = CastListAdapter(actorsList)
            castRecycler = binding.rvCast
            castRecycler?.adapter = castListAdapter
        }
    }

    override fun onDetach() {
        super.onDetach()
        castRecycler = null
    }

    companion object {
        private const val KEY_MOVIE_OBJECT = "KEY_MOVIE_OBJECT"

        fun createInstance(movie: Movie): FragmentMoviesDetails {
            return FragmentMoviesDetails().apply {
                arguments = bundleOf(
                    KEY_MOVIE_OBJECT to movie
                )
            }
        }
    }
}