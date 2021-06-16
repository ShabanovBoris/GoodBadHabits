package com.practice.goodbadhabits.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.data.local.HabitDataBase
import com.practice.goodbadhabits.data.local.HabitLocalDataSourceImpl
import com.practice.goodbadhabits.data.mappers.HabitApiResponseMapper
import com.practice.goodbadhabits.data.mappers.HabitEntityMapper
import com.practice.goodbadhabits.data.remote.HabitRemoteDataSourceImpl
import com.practice.goodbadhabits.data.remote.NetworkModule
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.ui.addition.AdditionViewModel
import com.practice.goodbadhabits.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers

class MainComponent(applicationContext: Context) {
    val viewModelFactory
        get() =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {

                    MainViewModel::class.java -> MainViewModel(
                        repository
                    )

                    DashboardViewModel::class.java -> DashboardViewModel()

                    AdditionViewModel::class.java -> AdditionViewModel(
                        repository
                    )


                    else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
                } as T
            }


    val repository: HabitRepository =
        HabitRepository(
            HabitLocalDataSourceImpl(
                HabitDataBase.getDatabase(applicationContext).habitDao(),
                Dispatchers.IO,
                HabitEntityMapper()
            ),
            HabitRemoteDataSourceImpl(
                NetworkModule().createHabitApi("https://droid-test-server.doubletapp.ru/"),
                HabitApiResponseMapper()
            )
        )

}