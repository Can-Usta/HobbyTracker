package com.example.hobbytracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "hobby")
data class Hobby(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title : String,
    var description : String,
    var date : Date
)
