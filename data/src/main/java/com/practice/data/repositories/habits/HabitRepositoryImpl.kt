package com.practice.data.repositories.habits

import com.practice.data.db.HabitLocalDataSource
import com.practice.data.api.HabitRemoteDataSource
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val localDataSource: HabitLocalDataSource,
    private val remoteDataSource: HabitRemoteDataSource
): HabitRepository {
   override suspend fun getHabitsCache(): Flow<List<com.practice.domain.entities.Habit>> {
        return localDataSource.getHabits()

    }

    override suspend fun insertHabitsCache(list: List<com.practice.domain.entities.Habit>) {
        localDataSource.insertHabits(list)
    }

    override suspend fun clearCache() {
        localDataSource.clear()
    }

    override suspend fun uploadHabit(habit: com.practice.domain.entities.Habit): String {
        return remoteDataSource.uploadHabit(habit)
    }

    override suspend fun deleteHabit(habitId: String) {
        return remoteDataSource.deleteHabit(habitId)
    }

    override suspend fun fetchHabits(): Flow<List<com.practice.domain.entities.Habit>> {
        return remoteDataSource.fetchHabits()
    }

    override suspend fun setDoneHabit(habitId: String, date: Long) {
        remoteDataSource.setDoneHabit(habitId, date)
    }

    override suspend fun deleteFromCache(habitId: String) {
        return localDataSource.deleteHabit(habitId)
    }

}