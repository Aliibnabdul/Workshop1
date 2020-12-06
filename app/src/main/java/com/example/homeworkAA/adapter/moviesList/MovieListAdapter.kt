package com.example.homeworkAA.adapter.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MovieListAdapter(private val adapterClickListener: AdapterClickListenerInterface) :
    RecyclerView.Adapter<MovieListViewHolder>() {
    var moviesList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ViewHolderMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.onBind(moviesList[position])
        holder.itemView.setOnClickListener {
            adapterClickListener.onClick(moviesList[position])
        }
    }
}

interface AdapterClickListenerInterface {
    fun onClick(movie: Movie)
}