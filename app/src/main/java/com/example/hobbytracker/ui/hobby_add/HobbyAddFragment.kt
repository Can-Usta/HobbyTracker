package com.example.hobbytracker.ui.hobby_add

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentHobbyAddBinding
import com.example.hobbytracker.receivers.HobbyAlarmReceiver
import com.example.hobbytracker.utils.DatePickerHelper
import com.example.hobbytracker.utils.TimePickerHelper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class HobbyAddFragment : Fragment() {
    private lateinit var binding: FragmentHobbyAddBinding
    private val viewModel: HobbyAddViewModel by viewModels()

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

                viewModel.saveHobbyToDB(
                    hobbyTitle = hobbyTitle,
                    hobbyDescription = hobbyDescription,
                    hobbyDate = hobbyDateTime
                )
                setHobbyAlarm(hobbyTitle, hobbyDateTime)
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

        // İzin verilmişse veya Android 13'ten düşük bir sürümse, alarmı ayarla
        scheduleAlarm(hobbyTitle, hobbyDate)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {

        // İzin isteğini doğrudan başlat
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }


    private fun scheduleAlarm(hobbyTitle: String, hobbyDate: LocalDateTime) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), HobbyAlarmReceiver::class.java).apply {
            putExtra("notificationId", hobbyDate.hashCode())
            putExtra("hobbyTitle", hobbyTitle)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            hobbyDate.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmTimeInMillis = hobbyDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,alarmTimeInMillis,
            pendingIntent
        )
    }
}