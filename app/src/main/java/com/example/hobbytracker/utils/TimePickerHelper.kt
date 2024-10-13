package com.example.hobbytracker.utils

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import java.time.LocalDateTime

class TimePickerHelper(private val context: Context) {
    fun showTimePickerDialog(onTimeSelected : (LocalDateTime) -> Unit){
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHours, selectedMinutes ->
            val selectedDateTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime().withHour(selectedHours).withMinute(selectedMinutes))
            onTimeSelected(selectedDateTime)
        }, hours, minutes, true)
        timePickerDialog.show()

    }
}