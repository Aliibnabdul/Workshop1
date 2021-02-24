package com.example.homeworkAA

import android.content.Intent
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
            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    override fun moveToFragment(movie: MovieEntity) {
        moveToFragment(movie.id)
    }

    private fun moveToFragment(id: Long) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragments_container, FragmentMovieDetails.newInstance(id))
                addToBackStack(null)
                setReorderingAllowed(true)
                commit()
            }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                id?.let { moveToFragment(id) }
            }
        }
    }
}