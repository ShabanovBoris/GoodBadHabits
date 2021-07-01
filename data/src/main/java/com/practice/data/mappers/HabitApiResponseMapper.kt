package com.practice.data.mappers

import com.practice.data.entities.HabitApiResponse

class HabitApiResponseMapper {

    private fun com.practice.domain.entities.Habit.toHabitApiResponse(): HabitApiResponse =
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

    private fun HabitApiResponse.toHabit(): com.practice.domain.entities.Habit =
        com.practice.domain.entities.Habit(
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


    fun toHabitApiResponseItemList(list: List<com.practice.domain.entities.Habit>): List<HabitApiResponse> =
        list.map { it.toHabitApiResponse() }

    fun toHabitList(list: List<HabitApiResponse>): List<com.practice.domain.entities.Habit> =
        list.map { it.toHabit() }

    fun mapToHabit(item: HabitApiResponse): com.practice.domain.entities.Habit = item.toHabit()

    fun mapToHabitApiResponseItem(habit: com.practice.domain.entities.Habit): HabitApiResponse = habit.toHabitApiResponse()
}