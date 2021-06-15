package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.data.remote.models.HabitApiResponse
import com.practice.goodbadhabits.data.remote.models.UidResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitApi {

@GET("api/habit")
suspend fun getHabits(): Flow<List<HabitApiResponse>>

@PUT("api/habit")
suspend fun putHabit(@Body habit: HabitApiResponse): UidResponse
}