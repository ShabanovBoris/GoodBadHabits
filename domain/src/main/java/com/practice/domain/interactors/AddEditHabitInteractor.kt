package com.practice.domain.interactors

import com.practice.domain.entities.Habit
import com.practice.domain.repositories.HabitRepository

class AddEditHabitInteractor(
    private val repository: HabitRepository
    ) {

    suspend fun setHabitDone(habitId: String, date: Long) =
        repository.setDoneHabit(habitId, date)

    /**
     * @return id deleted habit
     */
    suspend fun uploadHabit(habit: Habit): String =
        repository.uploadHabit(habit)

    suspend fun insertHabitCache(list: List<Habit>) =
        repository.insertHabitsCache(list)
}