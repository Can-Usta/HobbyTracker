package com.example.hobbytracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hobbytracker.data.local.entities.Hobby
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyTrackerDao {
    @Query("SELECT * FROM hobby")
    fun getHobbies(): Flow<List<Hobby>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHobby(hobby: Hobby)
}