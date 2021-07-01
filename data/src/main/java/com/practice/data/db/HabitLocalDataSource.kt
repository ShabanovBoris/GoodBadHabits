package com.practice.data.db

import com.practice.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitLocalDataSource {
    suspend fun getHabits(): Flow<List<Habit>>
    suspend fun insertHabits(list: List<Habit>)
    suspend fun clear()
    suspend fun deleteHabit(habitId: String)
}