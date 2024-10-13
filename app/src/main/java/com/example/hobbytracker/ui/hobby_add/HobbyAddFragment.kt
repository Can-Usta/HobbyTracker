package com.example.hobbytracker.ui.hobby_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentHobbyAddBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HobbyAddFragment : Fragment() {
    private lateinit var binding : FragmentHobbyAddBinding
    private val viewModel : HobbyAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hobby_add, container, false)

        binding.apply {
            hobbySaveButton.setOnClickListener {
                val hobbyTitle = hobbyTitleET.text.toString()
                val hobbyDescription = hobbyDescriptionET.text.toString()
                val hobbyDate = LocalDate.parse(hobbyDateET.text, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val hobbyTime = LocalTime.parse(hobbyTimeET.text, DateTimeFormatter.ofPattern("HH:mm"))

                val hobbyDateTime =LocalDateTime.of(hobbyDate,hobbyTime)

                viewModel.saveHobbyToDB(hobbyTitle = hobbyTitle, hobbyDescripton = hobbyDescription, hobbyDate = hobbyDateTime)
            }
        }
        return binding.root
    }
}