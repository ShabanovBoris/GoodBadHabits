package com.practice.domain.interactors

import com.practice.domain.entities.Habit
import com.practice.domain.repositories.HabitRepository

class AddEditHabitInteractor ( private val repository: HabitRepository) {

    suspend fun setHabitDone(habitId: String, date: Long){
        repository.setDoneHabit(habitId, date)
    }

    suspend fun insertHabitCache(list: List<Habit>) = repository.insertHabitsCache(list)
}