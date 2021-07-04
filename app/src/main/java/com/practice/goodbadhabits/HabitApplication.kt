package com.practice.goodbadhabits

import android.app.Application
import androidx.core.content.ContextCompat
import com.practice.goodbadhabits.di.AppComponent
import com.practice.goodbadhabits.di.DaggerAppComponent
import com.practice.goodbadhabits.utils.NightModeHelper

class HabitApplication : Application() {

    lateinit var appComponent: AppComponent private set



    override fun onCreate() {
        super.onCreate()
        NightModeHelper(this).setUpNightModePreference()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}