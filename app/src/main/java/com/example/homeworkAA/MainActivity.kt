package com.example.homeworkAA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkAA.data.models.Movie
import com.example.homeworkAA.databinding.ActivityMainBinding

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
                        commit()
                    }
        }
    }

    override fun moveToFragment(movie: Movie) {
        supportFragmentManager.beginTransaction()
                .apply {
                    replace(R.id.fragments_container, FragmentMoviesDetails.newInstance(movie))
                    addToBackStack(null)
                    commit()
                }
    }
}