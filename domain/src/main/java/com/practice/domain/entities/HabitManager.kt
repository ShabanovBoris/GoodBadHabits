package com.practice.domain.entities

import com.practice.domain.entities.Habit
import java.util.*

class HabitManager(habit: Habit) {

    private val creatureDate = habit.createDate
    private val doneCount = habit.doneDates.size
    private val repeat = habit.count
    private val frequency = habit.repeatDays
    private val habitType = habit.type

    val isDoneLoop = ((Date().time - creatureDate) / 1000 / 60 / 60 / 24) >= frequency
    val isCompleted = (repeat - doneCount) <= 0

    fun showMessage(isMessageBeforeChanges: Boolean = false): String {
        var diff = repeat - doneCount

        if (isMessageBeforeChanges){
            diff -= 1
        }

        return when (habitType) {
            Habit.Type.GOOD.ordinal -> if (diff > 0) "Remains to do $diff times" else "You are breathtaking!"

            Habit.Type.BAD.ordinal -> if (diff > 0) "You can do it $diff more times" else "Stop doing it"

            else -> throw IllegalArgumentException("Incorrect habit type")
        }
    }


}
