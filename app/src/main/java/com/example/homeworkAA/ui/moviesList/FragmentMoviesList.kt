package com.example.homeworkAA.ui.moviesList

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkAA.R
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.databinding.FragmentMoviesListBinding
import com.example.homeworkAA.di.Injection

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var listener: ClickListener

    @ExperimentalPagingApi
    private val moviesViewModel: MoviesListViewModel by viewModels { Injection.provideListViewModelFactory() }

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

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerColumns = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 2
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 3
        }

        val moviesListAdapter = MoviesListAdapter(listener::moveToFragment)
        val recyclerLayoutManager = GridLayoutManager(requireContext(), recyclerColumns)

        binding.rvMovies.apply {
            layoutManager = recyclerLayoutManager
            adapter = moviesListAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { moviesListAdapter.retry() },
                footer = LoadingStateAdapter { moviesListAdapter.retry() }
            )
        }

        moviesListAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                }
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(getString(R.string.check_internet_connection))
                        .setPositiveButton(getString(R.string.message_retry)) { _, _ ->
                            moviesListAdapter.retry()
                        }
                        .setCancelable(false)
                        .show()
                }
            }

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }

        moviesViewModel.liveDataPagingData.observe(viewLifecycleOwner, {
            moviesListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    interface ClickListener {
        fun moveToFragment(movie: MovieEntity)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}