package com.example.homeworkAA.ui.moviesList

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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

        val moviesListAdapter = MoviesListAdapter(::onRecyclerItemClick)
        val recyclerLayoutManager = GridLayoutManager(requireContext(), recyclerColumns)

        binding.rvMovies.apply {
            layoutManager = recyclerLayoutManager
            adapter = moviesListAdapter
                .withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter { moviesListAdapter.retry() },
                    footer = LoadingStateAdapter { moviesListAdapter.retry() }
                )
        }

        var isAlertWasShown = false
        moviesListAdapter.addLoadStateListener { loadState ->
            Log.d("LOADSTATE_TAG", "loadState.mediator?.refresh: ${loadState.mediator?.refresh}")
            when (loadState.mediator?.refresh) {
                is LoadState.NotLoading -> {
                }
                is LoadState.Loading -> {
                    isAlertWasShown = false
                }
                is LoadState.Error -> {
                    if (!isAlertWasShown){
                        isAlertWasShown = true
                        AlertDialog.Builder(requireContext())
                            .setMessage(getString(R.string.check_internet_connection))
                            .setPositiveButton(getString(R.string.retry)) { _, _ ->
                                moviesListAdapter.retry()
                            }
                            .setCancelable(true)
                            .show()
                    }
                }
            }

            binding.apply {
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                rvMovies.isVisible = loadState.mediator?.refresh !is LoadState.Loading
            }
        }

        moviesViewModel.liveDataPaging.observe(viewLifecycleOwner) {
            moviesListAdapter.submitData(lifecycle, it)
        }

        moviesViewModel.liveDataPosition.observe(viewLifecycleOwner) {
            Log.d("OBSERVER_TAG", "position $it")
            binding.rvMovies.scrollToPosition(it)
        }
    }

    @ExperimentalPagingApi
    private fun onRecyclerItemClick(movie: MovieEntity, pos: Int) {
        moviesViewModel.savePosition(pos)
        listener.moveToFragment(movie)
    }

    interface ClickListener {
        fun moveToFragment(movie: MovieEntity)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}