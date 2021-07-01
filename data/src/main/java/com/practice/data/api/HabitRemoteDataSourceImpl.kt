package com.practice.data.api

import android.util.Log
import com.practice.data.entities.HabitDoneRequest
import com.practice.data.entities.HabitUid
import com.practice.data.mappers.HabitApiResponseMapper
import com.practice.domain.entities.Habit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

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

    /**
     * Handle failure response case
     */
    override suspend fun fetchHabits(): Flow<List<Habit>> =
        flow {
            emit(
                mapper.toHabitList(api.getHabits())
            )
        }.retryWhen { cause, attempt ->
            Log.e("http", "$cause \n  attempt $attempt")
            if ((cause is retrofit2.HttpException) or
                (cause is TimeoutException) or
                (cause is UnknownHostException) and
                (attempt < 3)
            ) {
                delay(5000)
                true
            } else {
                false
            }
        }

    override suspend fun setDoneHabit(habitId: String, date: Long) =
        api.setDoneHabit(HabitDoneRequest(date, habitId))



}