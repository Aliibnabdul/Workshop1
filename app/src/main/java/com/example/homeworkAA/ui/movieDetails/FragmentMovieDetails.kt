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
            buttonSchedule.setOnClickListener {
                pickDateTimeAndSchedule(movie)
            }
        }
    }

    private fun pickDateTimeAndSchedule(movie: Movie) {
        val currentDateTime = Calendar.getInstance()
        val currentYear = currentDateTime.get(Calendar.YEAR)
        val currentMonth = currentDateTime.get(Calendar.MONTH)
        val currentDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), { _, year, month, day ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                val startDateTime: Long = Calendar.getInstance().run {
                    set(year, month, day, hour, minute)
                    timeInMillis
                }
                val endDateTime: Long = Calendar.getInstance().run {
                    set(year, month, day, hour, minute + 20)
                    timeInMillis
                }
                startCalendar(startDateTime, endDateTime, movie)

            }, currentHour, currentMinute, false).show()
        }, currentYear, currentMonth, currentDay).show()
    }

    private fun startCalendar(startDateTime: Long, endDateTime: Long, movie: Movie) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDateTime)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDateTime)
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