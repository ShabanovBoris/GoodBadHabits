package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRemoteDataSource {
    suspend fun uploadHabit(habit: Habit): String
    suspend fun deleteHabit(habitId: String)
    suspend fun fetchHabits(): Flow<List<Habit>>
    suspend fun setDoneHabit(habitId: String, date:Long)
}