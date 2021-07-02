package com.practice.goodbadhabits.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.data.db.HabitDataBase
import com.practice.data.db.impl.HabitLocalDataSourceImpl
import com.practice.data.mappers.HabitEntityMapper
import com.practice.data.api.impl.HabitRemoteDataSourceImpl
import com.practice.data.mappers.HabitApiResponseMapper
import com.practice.data.api.NetworkModule
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.domain.repositories.HabitRepository
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
                        AddEditHabitInteractor(repositoryImpl),
                        DeleteHabitInteractor(repositoryImpl),
                        GetHabitInteractor(repositoryImpl)
                    )

                    AdditionViewModel::class.java -> AdditionViewModel(
                        AddEditHabitInteractor(repositoryImpl),
                        DeleteHabitInteractor(repositoryImpl),
                        GetHabitInteractor(repositoryImpl)
                    )

                    DashboardViewModel::class.java -> DashboardViewModel(
                        GetHabitInteractor(repositoryImpl)
                    )


                    else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
                } as T
            }


    val repositoryImpl: HabitRepository =
        HabitRepositoryImpl(
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