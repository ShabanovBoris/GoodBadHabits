package com.practice.domain.interactors

import android.util.Log
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
    

    suspend fun getHabitCache(): Flow<HabitResult> =
        repository.getHabitsCache()


    private suspend fun checkHabitListOnStale(list: List<Habit>) {
        list.forEach {
            if (HabitManager(it).isDoneLoop) {
                try {
                    repository.deleteFromCache(it.id)
                    repository.deleteHabit(it.id)
                } catch (e: Exception) {
                    Log.e("TAG", "checkHabitListOnStale: error${e.localizedMessage}")
                    e.printStackTrace()
                } finally {
                    it.id = it.run {
                        id = ""
                        createDate = Date().time
                        isCompleted = false
                        return@run repository.uploadHabit(it)
                    }
                }
            }
            if (HabitManager(it).isCompleted) {
                it.isCompleted = true
            }
        }
    }


}