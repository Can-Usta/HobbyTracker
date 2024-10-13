package com.example.hobbytracker.ui.hobby_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobbytracker.data.local.entities.Hobby
import com.example.hobbytracker.data.local.repositories.HobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HobbyAddViewModel @Inject constructor(private val repository: HobbyRepository) : ViewModel() {

    fun saveHobbyToDB(hobbyTitle : String, hobbyDescripton: String, hobbyDate: LocalDateTime){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val newHobby = Hobby( title = hobbyTitle, description = hobbyDescripton, date = hobbyDate)
                repository.insertHobby(newHobby)
            }

        }
    }

}