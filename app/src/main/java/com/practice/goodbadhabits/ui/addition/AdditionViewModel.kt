package com.practice.goodbadhabits.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.launch

class AdditionViewModel(private val repository: HabitRepository) : ViewModel() {

    fun addHabit(habit: Habit){
        viewModelScope.launch {
            repository.uploadHabit(habit)
        }
    }
}