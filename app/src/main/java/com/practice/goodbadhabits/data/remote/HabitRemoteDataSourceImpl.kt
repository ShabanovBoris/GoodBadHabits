package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.data.mappers.HabitApiResponseMapper
import com.practice.goodbadhabits.entities.Habit

class HabitRemoteDataSourceImpl(
    private val api: HabitApi,
    private val mapper: HabitApiResponseMapper
) : HabitRemoteDataSource {
    override suspend fun uploadHabit(habit: Habit): String {
        val response = api.putHabit(mapper.mapToHabitApiResponseItem(habit))
        return response.uid ?: "-1"
    }

    override suspend fun deleteHabit(habit: Habit): String {
        return "stub"
    }

    override suspend fun fetchHabits(): List<Habit> =
        mapper.toHabitList(api.getHabits())

}