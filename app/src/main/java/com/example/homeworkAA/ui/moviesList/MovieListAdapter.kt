package com.example.homeworkAA.ui.moviesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ViewHolderMovieBinding

class MovieListAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListViewHolder>() {
    var moviesList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()

//            val diffUtilsCallback = MoviesDiffUtil(moviesList, value)
//            field = value
//            val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)
//            diffResult.dispatchUpdatesTo(this)
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
        holder.apply {
            onBind(moviesList[position])
            itemView.setOnClickListener {
                listener(moviesList[position])
            }
        }
    }
}
