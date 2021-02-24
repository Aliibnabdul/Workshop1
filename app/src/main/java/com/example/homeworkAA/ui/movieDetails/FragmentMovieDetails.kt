package com.example.homeworkAA.ui.movieDetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.homeworkAA.R
import com.example.homeworkAA.databinding.FragmentMoviesDetailsBinding
import com.example.homeworkAA.di.Injection
import com.example.homeworkAA.domain.models.Movie
import com.example.homeworkAA.extensions.movieIdBundle
import java.util.*

class FragmentMovieDetails : Fragment() {
    private lateinit var binding: FragmentMoviesDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        Injection.provideDetailsViewModelFactory(arguments.movieIdBundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieDetailsViewModel.movieLiveData.observe(viewLifecycleOwner) {
            initViews(it)
            initScheduleBlock(it)
        }
    }

    private fun initViews(movie: Movie) {
        binding.apply {
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            Glide.with(binding.root.context)
                .load(movie.backdropUrl)
                .into(binding.ivBackImage)
            tvAgeLimit.text =
                resources.getString(R.string.age_limit_13plus, movie.minimumAge)
            tvGenre.text = movie.genres
            ratingBar.rating = movie.ratings
            tvReviews.text =
                resources.getString(R.string.movie_reviews, movie.numberOfRatings)
            tvName.text = movie.title
            tvDescription.text = movie.overview
            tvCast.visibility = if (movie.actors.isEmpty()) {
                View.GONE
            } else {
                rvCast.adapter = CastListAdapter(movie.actors)
                View.VISIBLE
            }


        }
    }

    private fun initScheduleBlock(movie: Movie) {
        var scheduleYear = 0
        var scheduleMonth = 0
        var scheduleDay = 0
        var scheduleHour = 0
        var scheduleMinute = 0

        binding.apply {
            buttonSchedule.setOnClickListener {
                tvScheduleDate.visibility = View.VISIBLE
                tvScheduleTime.visibility = View.VISIBLE

                if (scheduleYear != 0 &&
                    scheduleMonth != 0 &&
                    scheduleDay != 0 &&
                    scheduleHour != 0 &&
                    scheduleMinute != 0
                ) {
                    val startMillis: Long = Calendar.getInstance().run {
                        set(scheduleYear, scheduleMonth, scheduleDay, scheduleHour, scheduleMinute)
                        timeInMillis
                    }
                    val endMillis: Long = Calendar.getInstance().run {
                        set(
                            scheduleYear,
                            scheduleMonth,
                            scheduleDay,
                            scheduleHour,
                            scheduleMinute + 20
                        )
                        timeInMillis
                    }
                    val intent = Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                        .putExtra(CalendarContract.Events.TITLE, "Cinema")
                        .putExtra(CalendarContract.Events.DESCRIPTION, movie.title)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "Silver screen")
                        .putExtra(
                            CalendarContract.Events.AVAILABILITY,
                            CalendarContract.Events.AVAILABILITY_BUSY
                        )
                        .putExtra(Intent.EXTRA_EMAIL, "ira@example.com, svetka@example.com")
                    startActivity(intent)
                }
            }

            tvScheduleDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    requireContext(),
                    { p0, p1, p2, p3 ->
                        scheduleYear = p1
                        scheduleMonth = p2 + 1
                        scheduleDay = p3
                        val dateString = "$scheduleDay . $scheduleMonth . $scheduleYear"
                        tvScheduleDate.text = dateString
                    },
                    year,
                    month,
                    day
                ).show()
            }

            tvScheduleTime.setOnClickListener {
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)

                TimePickerDialog(
                    requireContext(),
                    { p1, p2, p3 ->
                        scheduleHour = p2
                        scheduleMinute = p3
                        val timeString = "$scheduleHour : $scheduleMinute"
                        tvScheduleTime.text = timeString
                    },
                    hour,
                    minute,
                    true
                ).show()
            }
        }
    }

    companion object {
        fun newInstance(id: Long): FragmentMovieDetails {
            return FragmentMovieDetails().apply {
                arguments = Bundle().also {
                    it.movieIdBundle = id
                }
            }
        }
    }
}