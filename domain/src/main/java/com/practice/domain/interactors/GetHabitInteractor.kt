package com.practice.domain.interactors

import com.practice.domain.common.HabitResult
import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.*
import java.util.*

class GetHabitInteractor(
    private val repository: HabitRepository
) {

    suspend fun getHabits(): Flow<List<Habit>> {
        return repository.fetchHabits()
            .onEach(::checkHabitListOnStale)
    }

    suspend fun getRawHabitCache(): Flow<List<Habit>> = repository.getHabitsCache()

    suspend fun getHabitCache(): Flow<HabitResult> {
        return repository.getHabitsCache()
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

    suspend fun searchInCache(
        habitTitle: String,
        isOnlyNotCompleted: Boolean,
        colorSearchFilter: Int?
    ): Flow<HabitResult> {
        return repository.getHabitsCache()
            .map { list ->
                var resultList = list

                resultList = resultList.filter { habit ->
                    habit.title.contains(habitTitle, true)
                }

                if (isOnlyNotCompleted) {
                    resultList = resultList.filter { habit -> habit.isCompleted == false }
                }

                if (colorSearchFilter != null) {
                    resultList = resultList.filter { habit -> habit.colorId == colorSearchFilter }
                }

                return@map HabitResult.ValidResult(
                    resultList.filter { it.type == Habit.Type.GOOD.ordinal },
                    resultList.filter { it.type == Habit.Type.BAD.ordinal }
                )
            }
    }


    private suspend fun checkHabitListOnStale(list: List<Habit>) {
        list.forEach {
            if (HabitManager(it).isDoneLoop) {
                check(
                    repository.uploadHabit(
                        it.copy(
                            createDate = Date().time,
                            count = 0,
                            isCompleted = false
                        )
                    ) != "-1"
                )
            }
            if (HabitManager(it).isCompleted) {
                it.isCompleted = true
            }
        }
    }


}