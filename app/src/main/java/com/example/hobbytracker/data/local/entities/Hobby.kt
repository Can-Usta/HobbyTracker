package com.example.hobbytracker.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "hobby")
@Parcelize
data class Hobby(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title : String,
    var description : String,
    var date : LocalDateTime
): Parcelable{
    val dateFormatted: String
        get() = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val timeFormatted : String
        get() = date.format(DateTimeFormatter.ofPattern("HH:mm"))
}
