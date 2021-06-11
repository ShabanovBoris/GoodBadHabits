package com.practice.goodbadhabits

import android.app.Application
import com.practice.goodbadhabits.di.MainComponent

class HabitApplication: Application() {
    lateinit var component : MainComponent
    override fun onCreate() {
        super.onCreate()
        component  = MainComponent(applicationContext)
    }
}