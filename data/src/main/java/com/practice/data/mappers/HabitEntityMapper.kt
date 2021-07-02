package com.practice.data.mappers

import com.practice.data.dto.HabitEntity

class HabitEntityMapper {
    private fun HabitEntity.toHabit(): com.practice.domain.entities.Habit =
        com.practice.domain.entities.Habit(
            title = title,
            colorId = color,
            repeat = repeat,
            isCompleted = isCompleted,
            date = date,
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
            repeat = repeat,
            isCompleted = isCompleted ?: false,
            date = date,
            doneDates = HabitEntity.DoneDates(doneDates),
            count = count,
            description = description,
            priority = priority,
            type = type
        )

    fun toHabitEntityList(list: List<com.practice.domain.entities.Habit>): List<HabitEntity> =
        list.map { it.toHabitEntity() }
}