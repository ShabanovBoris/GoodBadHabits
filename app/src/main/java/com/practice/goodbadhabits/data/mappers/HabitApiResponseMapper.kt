package com.practice.goodbadhabits.data.mappers

import com.practice.goodbadhabits.data.remote.models.HabitApiResponse
import com.practice.goodbadhabits.entities.Habit
import java.util.*

class HabitApiResponseMapper {

    private fun Habit.toHabitApiResponse(): HabitApiResponse =
        HabitApiResponse(
            id = id,
            title = title,
            colorId = colorId,
            frequency = repeat,
            date = date ?: 0,
            doneDates = doneDates?: emptyList(),
            count = count,
            description = description,
            priority = priority,
            type = type
        )

    private fun HabitApiResponse.toHabit(): Habit =
        Habit(
            id = id,
            title = title,
            colorId = colorId,
            repeat = frequency,
            date = date,
            doneDates = doneDates,
            count = count,
            description = description,
            priority = priority,
            type = type,
            isCompleted = doneDates.isNotEmpty()
        )


    fun toHabitApiResponseItemList(list: List<Habit>): List<HabitApiResponse> =
        list.map { it.toHabitApiResponse() }

    fun toHabitList(list: List<HabitApiResponse>): List<Habit> =
        list.map { it.toHabit() }

    fun mapToHabit(item: HabitApiResponse): Habit = item.toHabit()

    fun mapToHabitApiResponseItem(habit: Habit): HabitApiResponse = habit.toHabitApiResponse()
}