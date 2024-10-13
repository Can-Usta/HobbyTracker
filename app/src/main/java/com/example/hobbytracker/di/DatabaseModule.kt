package com.example.hobbytracker.di

import android.content.Context
import com.example.hobbytracker.data.local.dao.HobbyTrackerDao
import com.example.hobbytracker.data.local.database.AppDatabase
import com.example.hobbytracker.data.local.repositories.HobbyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule  {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideBabyTrackerDao(db: AppDatabase) = db.hobbyTrackerDao()

    @Singleton
    @Provides
    fun provideRepository(localDataSource: HobbyTrackerDao) =
        HobbyRepository(localDataSource)


}