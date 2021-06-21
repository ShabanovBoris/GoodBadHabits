package com.practice.goodbadhabits.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.utils.logError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AdditionViewModel(private val repository: HabitRepository) : ViewModel() {


    private val handler = CoroutineExceptionHandler(::logError)


    fun addHabit(habit: Habit) =
        viewModelScope.launch(handler) {
            repository.uploadHabit(habit)
            repository.fetchHabits()
                .onEach {
                    repository.insertHabitsCache(it)
                }.launchIn(viewModelScope)
        }

}