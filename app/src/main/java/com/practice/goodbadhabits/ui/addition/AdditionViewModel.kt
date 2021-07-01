package com.practice.goodbadhabits.ui.addition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.data.utils.logError
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AdditionViewModel(private val repositoryImpl: HabitRepository) : ViewModel() {


    private val handler = CoroutineExceptionHandler(::logError)


    fun addHabit(habit: com.practice.domain.entities.Habit) =
        viewModelScope.launch(handler) {
            repositoryImpl.uploadHabit(habit)
            repositoryImpl.fetchHabits()
                .onEach {
                    repositoryImpl.insertHabitsCache(it)
                }.launchIn(viewModelScope)
        }

    fun delete(habitId: String) = viewModelScope.launch(handler) {
                repositoryImpl.deleteHabit(habitId)
                repositoryImpl.deleteFromCache(habitId)
    }

}