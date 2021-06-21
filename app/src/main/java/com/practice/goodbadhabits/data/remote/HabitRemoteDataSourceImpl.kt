package com.practice.goodbadhabits.data.remote

import android.util.Log
import com.practice.goodbadhabits.data.mappers.HabitApiResponseMapper
import com.practice.goodbadhabits.data.remote.models.HabitDoneRequest
import com.practice.goodbadhabits.data.remote.models.HabitUid
import com.practice.goodbadhabits.entities.Habit
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
     * After failure
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
                delay(10000)
                true
            } else {
                false
            }
        }
//            .catch { it.logStackTrace() }

    override suspend fun setDoneHabit(habitId: String, date: Int) =
        api.setDoneHabit(HabitDoneRequest(date, habitId))



}