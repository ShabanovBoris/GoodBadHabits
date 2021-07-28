package com.practice.goodbadhabits.interactors

import com.practice.domain.common.HabitResult.ValidResult
import com.practice.domain.entities.Habit
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.domain.repositories.HabitRepository
import com.practice.goodbadhabits.habit.getHabitMock
import com.practice.goodbadhabits.habit.getHabitMockList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*
import java.util.*

class GetHabitInteractorTest {
    private val oneDayInMillis = 1000 * 60 * 60 * 24
    private val repository: HabitRepository = mock()
    private val getHabitInteractor = GetHabitInteractor(repository)


    @Test
    fun `fetchHabits() return all habits`() = runBlocking {
        val list = getHabitMockList(8)
        whenever(repository.fetchHabits()).thenReturn(flowOf(list))
        //act
        var res = emptyList<Habit>()
        getHabitInteractor.getHabits().collect { res = it }
        //assert
        assertEquals(8, res.size)
    }

    @Test
    fun `ValidResult from cache has all habits in sum`() = runBlocking {
        val habit = getHabitMock()
        val habitResult = ValidResult(
            good = listOf(
                habit.copy(type = 0),
                habit.copy(type = 0),
                habit.copy(type = 0),
            ),
            bad = listOf(
                habit.copy(type = 1),
                habit.copy(type = 1),
                habit.copy(type = 1),
                habit.copy(type = 1),
            )
        )

        whenever(repository.getHabitsCache()).thenReturn(flowOf(habitResult))
        //act
        var res = ValidResult(emptyList(), emptyList())
        getHabitInteractor.getHabitCache().collect { res = it as ValidResult }
        //assert
        assertEquals(habitResult.bad!!.size + habitResult.good!!.size, res.bad!!.size + res.good!!.size)
    }

    @Test
    fun `swap on new habit when there are stale habits`() = runBlocking<Unit> {
        val date = Date().time
        val habit = getHabitMock().copy(isCompleted = true)
        var list = mutableListOf(
            habit.copy(id = "0", createDate = date - 2 * oneDayInMillis, repeatDays = 1), //stale 1
            habit.copy(id = "1", createDate = date, repeatDays = 2),                  //fresh
            habit.copy(id = "2", createDate = date - 3 * oneDayInMillis, repeatDays = 2), //stale 2
            habit.copy(id = "3", createDate = date - 2 * oneDayInMillis, repeatDays = 3), //fresh
            habit.copy(id = "4", createDate = date - 3 * oneDayInMillis, repeatDays = 3), //stale 3
            habit.copy(id = "5", createDate = date, repeatDays = 3),                  //fresh
            habit.copy(id = "6", createDate = date - 4 * oneDayInMillis, repeatDays = 4), //stale 4
            habit.copy(id = "7", createDate = date - 5 * oneDayInMillis, repeatDays = 6), //fresh
        )
        whenever(repository.fetchHabits()).thenReturn(flowOf(list))

        whenever(repository.uploadHabit(any())).doAnswer {
            val arg = it.arguments[0] as Habit
            list.forEachIndexed { index, habit ->
                if (habit.id == arg.id) list[index] = arg
            }
            return@doAnswer arg.id
        }
        //act
        getHabitInteractor.getHabits().collect { list = it.toMutableList() }
        //assert
        verify(repository, times(4)).uploadHabit(any())
        assert(list[0].createDate > date)
        assert(list[2].createDate > date)
        assert(list[4].createDate > date)
        assert(list[6].createDate > date)
        assert(list[3].createDate == date - 2 * oneDayInMillis)
        assertEquals(8, list.size)
    }


}