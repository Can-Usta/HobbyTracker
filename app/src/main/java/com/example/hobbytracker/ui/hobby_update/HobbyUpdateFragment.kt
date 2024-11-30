package com.example.hobbytracker.ui.hobby_update

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        Log.d("TAG", "hobbyId:${args.hobbyId}")
        if(args.hobbyId!=0){
            viewModel.getHobbyById(args.hobbyId)
            lifecycleScope.launch {
                viewModel.hobby.collect { hobby ->
                    hobby?.let {
                        binding.hobbyUpdateTitleET.setText(it.title)
                        binding.hobbyUpdateDescriptionET.setText(it.description)
                        binding.hobbyUpdateDateTV.text = it.dateFormatted
                        binding.hobbyUpdateTimeTV.text = it.timeFormatted
                    }
                }
            }
        }
        binding.apply {
            hobbyUpdateDateView.setOnClickListener {
                datePickerHelper.showDatePickerDialog { selectedDate ->
                    hobbyUpdateDateTV.text =
                        selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }
            }
            hobbyUpdateTimeView.setOnClickListener {
                timePickerHelper.showTimePickerDialog { selectedTime ->
                    hobbyUpdateTimeTV.text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                }
            }

            hobbyUpdateSaveButton.setOnClickListener {
                val title = hobbyUpdateTitleET.text.toString()
                val description = hobbyUpdateDescriptionET.text.toString()
                val hobbyDate = LocalDate.parse(hobbyUpdateDateTV.text, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val hobbyTime = LocalTime.parse(hobbyUpdateTimeTV.text, DateTimeFormatter.ofPattern("HH:mm"))
                val hobbyDateTime = LocalDateTime.of(hobbyDate,hobbyTime)

                val newHobby = Hobby(id = args.hobbyId, title = title, description = description, date = hobbyDateTime)
                viewModel.updateHobby(newHobby)

                loadingUpdateScreen.visibility= View.VISIBLE
                hobbyUpdateProgressBar.visibility= View.VISIBLE

                root.postDelayed({
                    hobbyUpdateProgressBar.visibility = View.GONE

                    updatedTextView.visibility = View.VISIBLE

                    root.postDelayed({
                        val action = HobbyUpdateFragmentDirections.actionHobbyUpdateFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }, 1000)
                }, 1000)
            }
        }

        return binding.root
    }

}