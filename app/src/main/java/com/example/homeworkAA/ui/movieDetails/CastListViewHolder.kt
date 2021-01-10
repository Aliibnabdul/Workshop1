package com.example.homeworkAA.ui.movieDetails

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkAA.data.models.Actor
import com.example.homeworkAA.databinding.CastRecyclerItemBinding

class CastListViewHolder(private val binding: CastRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(actor: Actor) {
        binding.apply {
            tvCast.text = actor.name
        }
        Glide.with(itemView.context)
            .load(actor.profilePath)
            .into(binding.ivCast)
    }
}