package com.example.homeworkAA.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MoviesListAdapter(private val listener: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MoviesListViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            ViewHolderMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.apply {
                onBind(repoItem)
                itemView.setOnClickListener {
                    listener(repoItem)
                }
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}