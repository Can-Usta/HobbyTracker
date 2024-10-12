package com.example.hobbytracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.hobbytracker.data.local.entities.Hobby
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyTrackerDao {
    @Query("SELECT * FROM hobby")
    fun getHobbies(): Flow<List<Hobby>>
}