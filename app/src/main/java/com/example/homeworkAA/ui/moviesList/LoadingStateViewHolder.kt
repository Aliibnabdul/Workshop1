package com.example.homeworkAA.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.databinding.LoadStateFooterBinding

class LoadingStateViewHolder(
    private val binding: LoadStateFooterBinding,
    onRetryClicked: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { onRetryClicked() }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> showLoadingState(false)
            is LoadState.Loading -> showLoadingState(true)
            is LoadState.Error -> {
                showLoadingState(false)
                binding.errorMsg.text = loadState.error.localizedMessage
            }
        }
    }

    private fun showLoadingState(state: Boolean) {
        binding.progressBar.isVisible = state
        binding.errorContainer.isVisible = !state
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadingStateViewHolder {
            val binding = LoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return LoadingStateViewHolder(binding, retry)
        }
    }
}