package com.example.hobbytracker.data.local.repositories

import com.example.hobbytracker.data.local.dao.HobbyTrackerDao
import com.example.hobbytracker.data.local.entities.Hobby
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HobbyRepository @Inject constructor(private val hobbyDao: HobbyTrackerDao) {
    fun insertHobby(hobby : Hobby){
        hobbyDao.insertHobby(hobby)
    }

    fun getHobbies():Flow<List<Hobby>> = flow{
        val response = hobbyDao.getHobbies()
        emit(response)
    }.flowOn(Dispatchers.IO)
}