package com.example.homeworkAA

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.adapter.moviesList.AdapterClickListenerInterface
import com.example.homeworkAA.adapter.moviesList.MovieListAdapter
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesListBinding
import com.example.homeworkAA.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private var listener: ClickListener? = null
    private var recycler: RecyclerView? = null
    private lateinit var movieListAdapter: MovieListAdapter

    private val clickListener = object : AdapterClickListenerInterface {
        override fun onClick(movie: Movie) {
            listener?.moveToFragment(movie)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListAdapter = MovieListAdapter(clickListener)
        recycler = binding.rvMovies
        recycler?.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler?.adapter = movieListAdapter
    }

    override fun onStart() {
        super.onStart()
        movieListAdapter.moviesList = MoviesDataSource().getMovies()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        recycler = null
    }

    interface ClickListener {
        fun moveToFragment(movie: Movie)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}