package com.example.homeworkAA

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.homeworkAA.MoviesConstants.WORK_TAG
import com.example.homeworkAA.data.db.entities.MovieEntity
import com.example.homeworkAA.databinding.ActivityMainBinding
import com.example.homeworkAA.ui.movieDetails.FragmentMovieDetails
import com.example.homeworkAA.ui.moviesList.FragmentMoviesList
import com.example.homeworkAA.workers.RefreshWorker
import java.util.concurrent.TimeUnit

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

        runWorkManager()
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

    private fun runWorkManager() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val constrainedRequest = PeriodicWorkRequest.Builder(RefreshWorker::class.java, 8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag(WORK_TAG)
            .setInitialDelay(8L, TimeUnit.HOURS)
            .build()

        workManager.enqueue(constrainedRequest)
    }
}