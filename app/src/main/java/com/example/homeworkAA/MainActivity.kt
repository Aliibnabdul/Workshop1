package com.example.homeworkAA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.databinding.ActivityMainBinding
import com.example.homeworkAA.ui.movieDetails.FragmentMovieDetails
import com.example.homeworkAA.ui.moviesList.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.ClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.fragments_container, FragmentMoviesList.newInstance())
                    setReorderingAllowed(true)
                    commit()
                }
        }
    }

    override fun moveToFragment(movie: MovieEntity) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragments_container, FragmentMovieDetails.newInstance(movie.id))
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
    }
}