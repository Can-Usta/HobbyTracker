package com.example.hobbytracker.utils

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import java.time.LocalDate

class DatePickerHelper(private val context :Context) {

    fun showDatePickerDialog(onDateSelected : (LocalDate) -> Unit){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(selectedDate)
        },year,month,day)

        datePickerDialog.show()
    }
}