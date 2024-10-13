package com.example.hobbytracker.di

import android.content.Context
import com.example.hobbytracker.utils.DatePickerHelper
import com.example.hobbytracker.utils.TimePickerHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
object PickerModule {

    @Provides
    fun provideContext(@ActivityContext context: Context): Context {
        return context
    }

    @Provides
    fun provideDatePicker(context: Context): DatePickerHelper {
        return DatePickerHelper(context)
    }
    @Provides
    fun provideTimePicker(context: Context): TimePickerHelper {
        return TimePickerHelper(context)
    }


}