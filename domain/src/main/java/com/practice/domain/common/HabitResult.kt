package com.practice.domain.common

import com.practice.domain.entities.Habit


sealed class HabitResult {
    data class ValidResult(
        val good: List<Habit>?,
        val bad: List<Habit>?
    ) : HabitResult()

    object EmptyResult : HabitResult()
    object EmptySearch: HabitResult()
}