package com.practice.data.api

import com.practice.data.entities.HabitDoneRequest
import com.practice.data.entities.HabitApiResponse
import com.practice.data.entities.HabitUid
import retrofit2.http.*

interface HabitApi {

    @GET("api/habit")
    suspend fun getHabits(): List<HabitApiResponse>

    @PUT("api/habit")
    suspend fun putHabit(
        @Body habit: HabitApiResponse
    ): HabitUid

    @HTTP(method = "DELETE", hasBody = true, path = "api/habit")
    suspend fun deleteHabit(
        @Body uid: HabitUid
    )

    @POST("api/habit_done")
    suspend fun setDoneHabit(
        @Body habitHabitDoneRequest: HabitDoneRequest
    )
}