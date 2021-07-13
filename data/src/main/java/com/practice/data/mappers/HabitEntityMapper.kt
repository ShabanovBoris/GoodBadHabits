package com.practice.data.mappers

import com.practice.data.dto.HabitEntity
import javax.inject.Inject

class HabitEntityMapper @Inject constructor() {
    private fun HabitEntity.toHabit(): com.practice.domain.entities.Habit =
        com.practice.domain.entities.Habit(
            title = title,
            colorId = color,
            repeatDays = repeat,
            isCompleted = isCompleted,
            createDate = date,
            id = id,
            doneDates = doneDates.dates,
            count = count,
            description = description,
            priority = priority,
            type = type,
        )

    fun toHabitList(list: List<HabitEntity>): List<com.practice.domain.entities.Habit> =
        list.map { it.toHabit() }

    private fun com.practice.domain.entities.Habit.toHabitEntity(): HabitEntity =
        HabitEntity(
            id = id,
            title = title,
            color = colorId,
            repeat = repeatDays,
            isCompleted = isCompleted ?: false,
            date = createDate,
            doneDates = HabitEntity.DoneDates(doneDates),
            count = count,
            description = description,
            priority = priority,
            type = type
        )

    fun toHabitEntityList(list: List<com.practice.domain.entities.Habit>): List<HabitEntity> =
        list.map { it.toHabitEntity() }
}