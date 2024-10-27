package com.example.hobbytracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hobbytracker.data.local.entities.Hobby

@Dao
interface HobbyTrackerDao {
    @Query("SELECT * FROM hobby")
    fun getHobbies(): List<Hobby>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHobby(hobby: Hobby)
}