package com.practice.goodbadhabits.data.mappers

import com.practice.goodbadhabits.data.local.entities.HabitEntity
import com.practice.goodbadhabits.entities.Habit

class HabitEntityMapper {
    private fun HabitEntity.toHabit(): Habit =
        Habit(
            title = title,
            colorId = color,
            repeat = repeat,
            isCompleted = isCompleted,
            date = date,
            id = id,
            doneDates = if (doneDates == "0"){
                emptyList()
            }else{
                doneDates
                    ?.split(",")
                    ?.map { it.toLong() }
            },
            count = count,
            description = description,
            priority = priority,
            type = type,
        )

    fun toHabitList(list: List<HabitEntity>): List<Habit> =
        list.map { it.toHabit() }

    private fun Habit.toHabitEntity(): HabitEntity =
        HabitEntity(
            id = id,
            title = title,
            color = colorId,
            repeat = repeat,
            isCompleted = isCompleted ?: false,
            date = date,
            doneDates = if(doneDates!!.isEmpty()){
                "0"
            }else{
                doneDates.joinToString(",")
            },
            count = count,
            description = description,
            priority = priority,
            type = type
        )

    fun toHabitEntityList(list: List<Habit>): List<HabitEntity> =
        list.map { it.toHabitEntity() }
}