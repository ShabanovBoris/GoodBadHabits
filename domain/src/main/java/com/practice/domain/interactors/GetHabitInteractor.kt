package com.practice.domain.interactors

import android.util.Log
import com.practice.domain.common.HabitResult
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.*

class GetHabitInteractor(
    private val repository: HabitRepository
) {

    suspend fun getHabit() = repository.fetchHabits()

    suspend fun getHabitCache(): Flow<HabitResult> {
        return repository.getHabitsCache()
            .distinctUntilChanged()
            .map { habitList ->
                if (habitList.isEmpty()) {
                    return@map HabitResult.EmptyResult
                } else {
                    return@map HabitResult.ValidResult(
                        habitList.filter { it.type == com.practice.domain.entities.Habit.Type.GOOD.ordinal },
                        habitList.filter { it.type == com.practice.domain.entities.Habit.Type.BAD.ordinal }
                    )

                }
            }
    }

    suspend fun searchInCache(
        habitTitle: String,
        isOnlyCompleted: Boolean,
        colorSearchFilter: Int?
    ): Flow<HabitResult> {
        return repository.getHabitsCache()
            .map { list ->
                var resultList = list

                resultList = resultList.filter { habit ->
                    habit.title.contains(habitTitle, true)
                }

                if (isOnlyCompleted) {
                    resultList = resultList.filter { habit -> habit.isCompleted == false }
                }

                if (colorSearchFilter != null) {
                    resultList = resultList.filter { habit -> habit.colorId == colorSearchFilter }
                }

                return@map HabitResult.ValidResult(
                    resultList.filter { it.type == com.practice.domain.entities.Habit.Type.GOOD.ordinal },
                    resultList.filter { it.type == com.practice.domain.entities.Habit.Type.BAD.ordinal }
                )
            }
    }


}