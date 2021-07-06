package com.practice.domain.interactors

import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import com.practice.domain.repositories.HabitRepository

class AddEditHabitInteractor(
    private val repository: HabitRepository
    ) {

    suspend fun setHabitDone(habit: Habit, date: Long) =
        repository.setDoneHabit(habit.id, date)


    /**
     * @return id deleted habit
     */
    suspend fun uploadHabit(habit: Habit): String =
        repository.uploadHabit(habit)

    suspend fun insertHabitCache(list: List<Habit>) =
        repository.insertHabitsCache(list)
}