package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.entities.Habit

interface HabitRemoteDataSource {
    suspend fun uploadHabit(habit: Habit): String
    suspend fun deleteHabit(habit: Habit): String
    suspend fun fetchHabits():List<Habit>
}