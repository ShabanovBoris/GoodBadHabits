package com.practice.goodbadhabits.data.local

import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitLocalDataSource {
    suspend fun getHabits(): Flow<List<Habit>>
    suspend fun insertHabits(list: List<Habit>)
    suspend fun clear()
}