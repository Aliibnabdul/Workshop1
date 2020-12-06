package com.example.homeworkAA.adapter.castList

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homeworkAA.data.models.Actor
import com.example.homeworkAA.databinding.CastRecyclerItemBinding

class CastListViewHolder(private val binding: CastRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(actor: Actor) {
        binding.apply {
            ivCast.load(actor.imageUrl)
            tvCast.text = actor.name
        }
    }
}