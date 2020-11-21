package com.example.homeworkAA

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkAA.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveOnMovieDetailsActivity()
        binding.apply {
            textViewActivity.setOnClickListener {
                moveOnMovieDetailsActivity()

            }
        }
    }

    private fun moveOnMovieDetailsActivity() {
        startActivity(Intent(this, MovieDetailsActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}