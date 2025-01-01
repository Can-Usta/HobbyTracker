package com.example.hobbytracker.ui.hobby_add

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentHobbyAddBinding
import com.example.hobbytracker.ui.home.HomeFragmentViewModel
import com.example.hobbytracker.utils.DatePickerHelper
import com.example.hobbytracker.utils.TimePickerHelper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class HobbyAddFragment : Fragment() {
    private lateinit var binding: FragmentHobbyAddBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    @Inject
    lateinit var datePickerHelper: DatePickerHelper

    @Inject
    lateinit var timePickerHelper: TimePickerHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hobby_add, container, false)

        binding.apply {
            hobbyDateView.setOnClickListener {
                datePickerHelper.showDatePickerDialog { selectedDate ->
                    hobbyDateTV.text =
                        selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }
            }
            hobbyTimeView.setOnClickListener {
                timePickerHelper.showTimePickerDialog { selectedTime ->
                    hobbyTimeTV.text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                }
            }
            hobbySaveButton.setOnClickListener {
                val hobbyTitle = hobbyTitleET.text.toString()
                val hobbyDescription = hobbyDescriptionET.text.toString()
                val hobbyDate =
                    LocalDate.parse(hobbyDateTV.text, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val hobbyTime =
                    LocalTime.parse(hobbyTimeTV.text, DateTimeFormatter.ofPattern("HH:mm"))
                val hobbyDateTime = LocalDateTime.of(hobbyDate, hobbyTime)

                loadingScreen.visibility = View.VISIBLE
                hobbyAddProgressBar.visibility = View.VISIBLE

                viewModel.saveHobbyToDB(
                    hobbyTitle = hobbyTitle,
                    hobbyDescription = hobbyDescription,
                    hobbyDate = hobbyDateTime
                )
                setHobbyAlarm(hobbyTitle, hobbyDateTime)
                root.postDelayed({
                    hobbyAddProgressBar.visibility = View.GONE

                    savedTextView.visibility = View.VISIBLE

                    root.postDelayed({
                        val action =
                            HobbyAddFragmentDirections.actionHobbyAddFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }, 1000)
                }, 1000)

            }
        }
        return binding.root
    }


    private fun setHobbyAlarm(hobbyTitle: String, hobbyDate: LocalDateTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission()

            }
        }
        viewModel.scheduleHobbyAlarm(hobbyTitle, hobbyDate, requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }
}