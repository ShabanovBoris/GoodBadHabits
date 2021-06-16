package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.data.mappers.HabitApiResponseMapper
import com.practice.goodbadhabits.data.remote.models.HabitDoneRequest
import com.practice.goodbadhabits.data.remote.models.HabitUid
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HabitRemoteDataSourceImpl(
    private val api: HabitApi,
    private val mapper: HabitApiResponseMapper
) : HabitRemoteDataSource {
    override suspend fun uploadHabit(habit: Habit): String {
        val response = api.putHabit(mapper.mapToHabitApiResponseItem(habit))
        return response.uid ?: "-1"
    }

    override suspend fun deleteHabit(habitId: String) {
        api.deleteHabit(HabitUid(habitId))
    }

    override suspend fun fetchHabits(): Flow<List<Habit>> =
        flow {
            emit(
                mapper.toHabitList(api.getHabits())
            )
        }

    override suspend fun setDoneHabit(habitId: String, date: Int) {
        api.setDoneHabit(HabitDoneRequest(date, habitId))
    }


}