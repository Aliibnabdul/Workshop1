package com.example.homeworkAA.ui.moviesList

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkAA.ViewModelFactory
import com.example.homeworkAA.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var listener: ClickListener

    private val moviesViewModel: MoviesListViewModel by viewModels { ViewModelFactory() }

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

        val movieListAdapter = MovieListAdapter(listener::moveToFragment)
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), recyclerColumns)
            adapter = movieListAdapter
        }

        moviesViewModel.moviesListLiveData.observe(viewLifecycleOwner, {
            movieListAdapter.moviesList = it
        })
    }

    override fun onResume() {
        super.onResume()
        moviesViewModel.refreshMoviesList(requireContext())
    }

    interface ClickListener {
        fun moveToFragment(id: Int)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}