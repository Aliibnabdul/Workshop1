package com.example.homeworkAA

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homeworkAA.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private var listener: ClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickListener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.avengersLayout.root.setOnClickListener {
            Log.d("LISTENER_TAG", "listener $listener")
            listener?.moveToFragment()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface ClickListener {
        fun moveToFragment()
    }

    companion object {
        fun newInstance() = FragmentMoviesList()
    }
}