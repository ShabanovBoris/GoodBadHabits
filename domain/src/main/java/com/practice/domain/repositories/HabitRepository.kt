package com.practice.domain.repositories

import com.practice.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    suspend fun getHabitsCache(): Flow<List<Habit>>

    suspend fun insertHabitsCache(list: List<Habit>)

    suspend fun clearCache()

    suspend fun uploadHabit(habit: Habit): String

    suspend fun deleteHabit(habitId: String)

    suspend fun fetchHabits(): Flow<List<Habit>>

    suspend fun setDoneHabit(habitId: String, date: Long)

    suspend fun deleteFromCache(habitId: String)


}