package com.example.homeworkAA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkAA.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentMoviesList.ClickListener {
    private lateinit var binding : ActivityMainBinding
    private val moviesListFragment = FragmentMoviesList().apply { setListener(this@MainActivity)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.fragments_container, moviesListFragment)
                    commit()
                }
        }
    }

    override fun replaceFragment() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragments_container, FragmentMoviesDetails())
                addToBackStack(null)
                commit()
            }
    }
}