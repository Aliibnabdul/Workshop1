package com.example.homeworkAA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkAA.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveOnMovieDetailsActivity()
        binding.apply {
            tvMainActivity.setOnClickListener {
                moveOnMovieDetailsActivity()
            }
        }
    }

    private fun moveOnMovieDetailsActivity() {
        startActivity(Intent(this, MovieDetailsActivity::class.java))
    }
}