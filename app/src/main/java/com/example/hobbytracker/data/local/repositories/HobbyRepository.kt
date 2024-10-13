package com.example.hobbytracker.data.local.repositories

import com.example.hobbytracker.data.local.dao.HobbyTrackerDao
import com.example.hobbytracker.data.local.entities.Hobby
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HobbyRepository @Inject constructor(private val hobbyDao: HobbyTrackerDao) {
    fun insertHobby(hobby : Hobby){
        hobbyDao.insertHobby(hobby)
    }

    fun getHobbies():Flow<List<Hobby>>{
        return hobbyDao.getHobbies()
    }
}