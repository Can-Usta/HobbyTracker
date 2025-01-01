package com.example.hobbytracker.ui.hobby_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobbytracker.data.local.entities.Hobby
import com.example.hobbytracker.data.local.repositories.HobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HobbyUpdateViewModel @Inject constructor(private val hobbyRepository: HobbyRepository) :
    ViewModel() {
    private val _hobby = MutableStateFlow<Hobby?>(null)
    val hobby: StateFlow<Hobby?> = _hobby
    private val _alertMessage = MutableStateFlow<String?>(null)
    val alertMessage: StateFlow<String?> = _alertMessage

    fun getHobbyById(hobbyId: Int) {
        viewModelScope.launch {
            hobbyRepository.getHobbyById(hobbyId).collect { fetchedHobby ->
                _hobby.value = fetchedHobby
            }
        }
    }

    fun updateHobby(hobby: Hobby) {
        viewModelScope.launch {
            hobbyRepository.updateHobby(hobby)
                .collect { success ->
                    if (success) {
                        _alertMessage.value = "Hobby Updated Successfully!"
                    } else {
                        _alertMessage.value = "Failed to Update Hobby!"
                    }
                }
        }
    }

    fun getUpdatedHobby(
        id: Int,
        title: String,
        description: String,
        date: String,
        time: String
    ): Hobby? {
        if (title.isBlank() || description.isBlank() || date.isBlank() || time.isBlank()) {
            return null
        }
        val hobbyDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val hobbyTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
        val hobbyDateTime = LocalDateTime.of(hobbyDate, hobbyTime)
        return Hobby(
            id = id,
            title = title,
            description = description,
            date = hobbyDateTime
        )
    }
}