package com.example.homeworkAA.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MoviesListAdapter(private val listener: (MovieEntity, Int) -> Unit) :
    PagingDataAdapter<MovieEntity, MoviesListViewHolder>(DIFF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            ViewHolderMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        getItem(position)?.let(holder::onBind)
    }

    companion object {
        private val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }
}