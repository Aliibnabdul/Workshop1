package com.example.homeworkAA

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.adapter.moviesList.MovieListAdapter
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesListBinding
import com.example.homeworkAA.domain.MoviesDataSource

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var listener: ClickListener
    private lateinit var movieListAdapter: MovieListAdapter

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

        movieListAdapter = MovieListAdapter(listener::moveToFragment)
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        movieListAdapter.moviesList = MoviesDataSource().getMovies()
    }

    interface ClickListener {
        fun moveToFragment(movie: Movie)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}