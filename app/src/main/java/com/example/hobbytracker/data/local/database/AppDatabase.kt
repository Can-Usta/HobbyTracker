package com.example.hobbytracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hobbytracker.data.local.converters.DateConverters
import com.example.hobbytracker.data.local.dao.HobbyTrackerDao
import com.example.hobbytracker.data.local.entities.Hobby

@Database(entities = [Hobby::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hobbyTrackerDao() : HobbyTrackerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hobby_tracker_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}