package com.example.hobbytracker.di

import android.content.Context
import com.example.hobbytracker.data.local.database.AppDatabase
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
}