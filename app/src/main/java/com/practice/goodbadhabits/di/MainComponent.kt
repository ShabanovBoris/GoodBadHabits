package com.practice.goodbadhabits.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.ui.MainViewModel
import com.practice.goodbadhabits.ui.addition.AdditionViewModel
import com.practice.goodbadhabits.ui.dashboard.DashboardViewModel

class MainComponent(applicationContext: Context) {
        val viewModelFactory get() =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {

                    MainViewModel::class.java -> MainViewModel(
                        this@MainComponent
                    )

                    DashboardViewModel::class.java -> DashboardViewModel()

                    AdditionViewModel::class.java -> AdditionViewModel()


                    else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
                } as T
            }


    val mockList get() = listOf(
        Habit("smoke", R.color.black, "1 times"),
        Habit("running", R.color.white, "3 times"),
        Habit("gym", R.color.secondaryColor_600, "4 times"),
    )

}