package com.practice.goodbadhabits.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: HabitRepository) : ViewModel() {


    private val _habitList = MutableSharedFlow<List<Habit>>(1)
    val habitList get() = _habitList.asSharedFlow()


    suspend fun getList() = repository.fetchHabits()

    private suspend fun setHabitListCache(list: List<Habit>) =
        repository.insertHabitsCache(list)

}