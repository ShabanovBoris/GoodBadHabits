package com.practice.goodbadhabits.habit

import androidx.annotation.ColorRes
import com.practice.domain.entities.Habit
import org.mockito.kotlin.times
import java.util.*



private val oneDayInMillis = 1000 * 60 * 60 * 24

 fun  getHabitMock() = Habit(
    title = "",
    colorId = 0,
    repeatDays = 5,
    isCompleted = false,
    createDate = Date().time,
    id = "",
    doneDates = listOf(
        Date().time + oneDayInMillis,
        Date().time + 2 * oneDayInMillis
    ),
    count = 3,
    description = "",
    priority = 0,
    type = 0
)

fun getHabitMockList(quantity: Int = 2): List<Habit> {
    val habit = getHabitMock()
    val list = mutableListOf<Habit>()

    repeat(quantity){
        list.add( habit.copy(id = it.toString()))
    }
    return list
}

