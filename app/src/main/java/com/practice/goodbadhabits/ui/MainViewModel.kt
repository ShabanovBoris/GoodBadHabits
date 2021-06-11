package com.practice.goodbadhabits.ui

import androidx.lifecycle.ViewModel
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.di.MainComponent
import com.practice.goodbadhabits.entities.Habit

class MainViewModel(val mainComponent: MainComponent) :ViewModel() {
    fun getList() = mainComponent.mockList
}