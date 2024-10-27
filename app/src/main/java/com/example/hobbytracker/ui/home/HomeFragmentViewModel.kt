package com.example.hobbytracker.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobbytracker.data.local.entities.Hobby
import com.example.hobbytracker.data.local.repositories.HobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val hobbyRepository: HobbyRepository) : ViewModel() {
    private val _hobby = MutableStateFlow<List<Hobby>>(emptyList())
    val hobby: StateFlow<List<Hobby>> = _hobby

    init {
        getAllHobby()
    }

    private fun getAllHobby(){
        viewModelScope.launch {
            hobbyRepository.getHobbies()
                .catch { e->
                    Log.e("HomeFragmentViewModel", "Error fetching hobbies: ${e.message}")
                }
                .collect{ response->
                    _hobby.value = response
                }
        }
    }

}