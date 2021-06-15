package com.practice.goodbadhabits

import android.app.Application
import com.practice.goodbadhabits.di.MainComponent
import com.practice.goodbadhabits.utils.NightModeHelper

class HabitApplication: Application() {
    var themeApp = R.style.Theme_GoodBadHabits
    lateinit var component : MainComponent
    override fun onCreate() {
        super.onCreate()
        NightModeHelper(this).setUpNightModePreference()
        component  = MainComponent(applicationContext)
    }
}