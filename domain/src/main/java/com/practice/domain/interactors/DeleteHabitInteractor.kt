package com.practice.domain.interactors

import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.collect

class DeleteHabitInteractor( private val repository: HabitRepository) {

    suspend fun clearData() {
        repository.fetchHabits().collect { list ->
            list.forEach { habit ->
                repository.deleteHabit(habit.id)
            }
        }
        repository.clearCache()
    }
}