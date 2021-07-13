package com.practice.data.mappers

import com.practice.data.dto.HabitApiResponse
import com.practice.domain.entities.Habit
import javax.inject.Inject

class HabitApiResponseMapper @Inject constructor() {

    private fun com.practice.domain.entities.Habit.toHabitApiResponse(): HabitApiResponse =
        HabitApiResponse(
            id = id,
            title = title,
            colorId = colorId,
            frequency = repeatDays,
            createDate = createDate,
            doneDates = doneDates,
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
            repeatDays = frequency,
            createDate = createDate,
            doneDates = doneDates,
            count = count,
            description = description,
            priority = priority,
            type = type,
            isCompleted = false
        )


    fun toHabitApiResponseItemList(list: List<Habit>): List<HabitApiResponse> =
        list.map { it.toHabitApiResponse() }

    fun toHabitList(list: List<HabitApiResponse>): List<Habit> =
        list.map { it.toHabit() }

    fun mapToHabit(item: HabitApiResponse): Habit = item.toHabit()

    fun mapToHabitApiResponseItem(habit: Habit): HabitApiResponse = habit.toHabitApiResponse()
}