package com.practice.goodbadhabits

import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class HabitManagerTest {

    private val oneDayInMillis = 1000 * 60 * 60 * 24

    private val habitMockGoodDone = Habit(
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
        count = 2,
        description = "",
        priority = 0,
        type = 0
    )
    private val habitMockBadDone = habitMockGoodDone.copy(type = 1)
    private val habitMockGood = habitMockGoodDone.copy(count = 5)
    private val habitMockBad = habitMockBadDone.copy(count = 6)


    @Test
    fun `Good Habit which is done`() {
        val manager = HabitManager(habitMockGoodDone)
        assertEquals(
            "You are breathtaking!",
            manager.showMessage()
        )
        assertEquals(
            true,
            manager.isCompleted
        )
    }
    @Test
    fun `Bad Habit which is done`() {
        val manager = HabitManager(habitMockBadDone)
        assertEquals(
            "Stop doing it",
            manager.showMessage()
        )
        assertEquals(
            true,
            manager.isCompleted
        )
    }
    @Test
    fun `Good Habit which is not completed`() {
        val manager = HabitManager(habitMockGood)
        assertEquals(
            "Remains to do 3 times",
            manager.showMessage()
        )
        assertEquals(
            false,
            manager.isCompleted
        )
    }
    @Test
    fun `Bad Habit which is not completed`() {
        val manager = HabitManager(habitMockBad)
        assertEquals(
            "You can do it 4 more times",
            manager.showMessage()
        )
        assertEquals(
            false,
            manager.isCompleted
        )
    }
    @Test
    fun `period of habit is end`(){
        val date = Date().time
        val habit = habitMockGood.copy(
            //3 days before
            createDate = date - 3*oneDayInMillis,
            //period 3 days
            repeatDays = 3
        )
        val manager = HabitManager(habit)

        assertEquals(
            true,
            manager.isDoneLoop
        )
    }
    @Test
    fun `period of habit is not end`(){
        val date = Date().time
        val habit = habitMockGood.copy(
            //2 days before
            createDate = date - 2*oneDayInMillis,
            //period 3 days
            repeatDays = 3
        )
        val manager = HabitManager(habit)

        assertEquals(
            false,
            manager.isDoneLoop
        )
    }

}