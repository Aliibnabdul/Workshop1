package com.example.homeworkAA.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkAA.data.domain.Actor
import com.example.homeworkAA.databinding.CastRecyclerItemBinding

class CastListAdapter(private val actorsList: List<Actor>) :
    RecyclerView.Adapter<CastListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListViewHolder {
        return CastListViewHolder(
            CastRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = actorsList.size

    override fun onBindViewHolder(holder: CastListViewHolder, position: Int) {
        holder.onBind(actorsList[position])
    }
}