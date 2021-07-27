package com.practice.data.repositories.habits

import com.practice.data.db.HabitLocalDataSource
import com.practice.data.api.HabitRemoteDataSource
import com.practice.domain.common.HabitResult
import com.practice.domain.entities.Habit
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val localDataSource: HabitLocalDataSource,
    private val remoteDataSource: HabitRemoteDataSource
): HabitRepository {


   override suspend fun getHabitsCache(): Flow<HabitResult> {
        return localDataSource.getHabits()
            .distinctUntilChanged()
            .map { habitList ->
                if (habitList.isEmpty()) {
                    return@map HabitResult.EmptyResult
                } else {
                    return@map HabitResult.ValidResult(
                        habitList.filter { it.type == Habit.Type.GOOD.ordinal },
                        habitList.filter { it.type == Habit.Type.BAD.ordinal }
                    )
                }
            }

    }

    override suspend fun insertHabitsCache(list: List<Habit>) {
        localDataSource.insertHabits(list)
    }

    override suspend fun clearCache() {
        localDataSource.clear()
    }

    override suspend fun uploadHabit(habit: Habit): String {
        return remoteDataSource.uploadHabit(habit)
    }

    override suspend fun deleteHabit(habitId: String) {
        return remoteDataSource.deleteHabit(habitId)
    }

    override suspend fun fetchHabits(): Flow<List<Habit>> {
        return remoteDataSource.fetchHabits()
    }

    override suspend fun setDoneHabit(habitId: String, date: Long) {
        remoteDataSource.setDoneHabit(habitId, date)
    }

    override suspend fun deleteFromCache(habitId: String) {
        return localDataSource.deleteHabit(habitId)
    }

}