package com.practice.data.api

import com.practice.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRemoteDataSource {

    suspend fun uploadHabit(habit: Habit): String

    suspend fun deleteHabit(habitId: String)

    suspend fun fetchHabits(): Flow<List<Habit>>

    suspend fun setDoneHabit(habitId: String, date:Long)
}