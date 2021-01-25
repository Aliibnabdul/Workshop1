package com.example.homeworkAA.ui.moviesList

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
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.FragmentMoviesListBinding
import com.example.homeworkAA.di.Injection
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var listener: ClickListener

    private val moviesViewModel: MoviesListViewModel by viewModels { Injection.provideListViewModelFactory() }
    private lateinit var moviesListAdapter: MoviesListAdapter
    private var searchJob: Job? = null

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

        moviesListAdapter = MoviesListAdapter(listener::moveToFragment)
        val recyclerLayoutManager = GridLayoutManager(requireContext(), recyclerColumns)

        binding.rvMovies.apply {
            layoutManager = recyclerLayoutManager
            adapter = moviesListAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { moviesListAdapter.retry() },
                footer = LoadingStateAdapter { moviesListAdapter.retry() }
            )
        }

        binding.retryButton.setOnClickListener { moviesListAdapter.retry() }

        moviesListAdapter.addLoadStateListener { loadState ->
            binding.rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            binding.tvCheckInternet.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Log.d("EXCEPTION_TAG", it.error.toString())
            }
        }

        val query = "now_playing"
        search(query)
    }

    @ExperimentalPagingApi
    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            moviesViewModel.getFlowPagingData(query).collectLatest {

                moviesListAdapter.submitData(it)
            }
        }
    }

    interface ClickListener {
        fun moveToFragment(movie: Movie)
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}