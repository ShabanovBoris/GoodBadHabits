package com.practice.goodbadhabits.data.remote

import com.practice.goodbadhabits.data.remote.models.HabitDoneRequest
import com.practice.goodbadhabits.data.remote.models.HabitApiResponse
import com.practice.goodbadhabits.data.remote.models.HabitUid
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