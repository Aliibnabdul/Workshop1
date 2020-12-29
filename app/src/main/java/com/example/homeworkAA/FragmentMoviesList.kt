package com.example.homeworkAA

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkAA.adapter.moviesList.MovieListAdapter
import com.example.homeworkAA.data.loadMovies
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesListBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var listener: ClickListener
    private lateinit var movieListAdapter: MovieListAdapter

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.d(
            "SCOPE_TAG", "CurrentThread: ${Thread.currentThread().name}. " +
                    "CoroutineExceptionHandler got $exception in $coroutineContext"
        )
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main + exceptionHandler)

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
        val recyclerColumns = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 3
        }
        movieListAdapter = MovieListAdapter(listener::moveToFragment)
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), recyclerColumns)
            adapter = movieListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        coroutineScope.launch {
            movieListAdapter.moviesList = loadMovies(requireContext())
        }
    }

    interface ClickListener {
        fun moveToFragment(movie: Movie)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}