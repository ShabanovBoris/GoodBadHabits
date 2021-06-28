package com.practice.goodbadhabits

import android.app.Application
import com.practice.goodbadhabits.di.MainComponent
import com.practice.goodbadhabits.utils.NightModeHelper

class HabitApplication : Application() {
    lateinit var component: MainComponent
    private set



    override fun onCreate() {
        super.onCreate()
        NightModeHelper(this).setUpNightModePreference()
        component = MainComponent(applicationContext)
    }
}