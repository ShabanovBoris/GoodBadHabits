package com.practice.goodbadhabits.data

import com.practice.goodbadhabits.data.local.HabitLocalDataSource
import com.practice.goodbadhabits.data.remote.HabitRemoteDataSource
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(
    private val localDataSource: HabitLocalDataSource,
    private val remoteDataSource: HabitRemoteDataSource
) {
    suspend fun getHabitsCache(): Flow<List<Habit>> {
        return localDataSource.getHabits()
    }

    suspend fun insertHabitsCache(list: List<Habit>) {
        localDataSource.insertHabits(list)
    }

    suspend fun clearCache() {
        localDataSource.clear()
    }

    suspend fun uploadHabit(habit: Habit): String {
        return remoteDataSource.uploadHabit(habit)
    }

    suspend fun deleteHabit(habitId: String) {
        return remoteDataSource.deleteHabit(habitId)
    }

    suspend fun fetchHabits(): Flow<List<Habit>> {
        return remoteDataSource.fetchHabits()
    }

    suspend fun setDoneHabit(habitId: String, date: Int){
        remoteDataSource.setDoneHabit(habitId,date)
    }

}