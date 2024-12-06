package com.example.hobbytracker.ui.hobby_update

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hobbytracker.R
import com.example.hobbytracker.data.local.entities.Hobby
import com.example.hobbytracker.databinding.FragmentHobbyUpdateBinding
import com.example.hobbytracker.utils.DatePickerHelper
import com.example.hobbytracker.utils.TimePickerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class HobbyUpdateFragment : Fragment() {
    private val args: HobbyUpdateFragmentArgs by navArgs()
    private lateinit var binding: FragmentHobbyUpdateBinding
    private val viewModel: HobbyUpdateViewModel by viewModels()

    @Inject
    lateinit var datePickerHelper: DatePickerHelper

    @Inject
    lateinit var timePickerHelper: TimePickerHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hobby_update, container, false)
        setupIU()
        observeViewModel()
        return binding.root
    }

    private fun setupIU() {
        binding.apply {
            hobbyUpdateDateView.setOnClickListener { showDatePicker() }
            hobbyUpdateTimeView.setOnClickListener { showTimePicker() }
            hobbyUpdateSaveButton.setOnClickListener { onSaveClicked() }
        }
    }

    private fun observeViewModel() {
        viewModel.getHobbyById(args.hobbyId)
        lifecycleScope.launch {
            viewModel.hobby.collect { hobby ->
                hobby?.let {
                    binding.hobby = it
                }
            }
        }
    }

    private fun showDatePicker() {
        datePickerHelper.showDatePickerDialog { selectedDate ->
            binding.hobbyUpdateDateTV.text =
                selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

    private fun showTimePicker() {
        timePickerHelper.showTimePickerDialog { selectedTime ->
            binding.hobbyUpdateTimeTV.text =
                selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }

    private fun onSaveClicked() {
        val title = binding.hobbyUpdateTitleET.text.toString()
        val description = binding.hobbyUpdateDescriptionET.text.toString()

        val dateText = binding.hobbyUpdateDateTV.text.toString()
        val timeText = binding.hobbyUpdateTimeTV.text.toString()
        val updatedHobby =
            viewModel.getUpdatedHobby(args.hobbyId, title, description, dateText, timeText)
        updatedHobby?.let {
            viewModel.updateHobby(it)
            showLoading()
            navigateToHomeWithDelay()
        }
    }

    private fun showLoading() {
        binding.apply {
            loadingUpdateScreen.visibility = View.VISIBLE
            hobbyUpdateProgressBar.visibility = View.VISIBLE
        }
    }

    private fun navigateToHomeWithDelay() {
        binding.apply {
            root.postDelayed({
                hobbyUpdateProgressBar.visibility = View.GONE
                updatedTextView.visibility = View.VISIBLE

                root.postDelayed({
                    val action =
                        HobbyUpdateFragmentDirections.actionHobbyUpdateFragmentToHomeFragment()
                    findNavController().navigate(action)
                }, 1000)
            }, 1000)
        }
    }

}