package com.practice.goodbadhabits.viewmodel

import com.practice.domain.common.HabitResult
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.goodbadhabits.TestCoroutineRule
import com.practice.goodbadhabits.habit.getHabitMock
import com.practice.goodbadhabits.habit.getHabitMockList
import com.practice.goodbadhabits.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()


    private val addEditHabitInteractor: AddEditHabitInteractor = mock()
    private val deleteHabitInteractor: DeleteHabitInteractor = mock()
    private val getHabitInteractor: GetHabitInteractor = mock()


    @ExperimentalCoroutinesApi
    @Test
    fun `initialization view model working correct`() =
        coroutineRule.testDispatcher.runBlockingTest {
            //arrange
            val list = getHabitMockList()
            val resultNetwork: HabitResult = HabitResult.ValidResult(list, emptyList())

            whenever(getHabitInteractor.getHabitCache()).thenReturn(flowOf(resultNetwork))
            whenever(getHabitInteractor.getHabits()).thenReturn(flowOf(list))

            //act
            val mainViewMode = MainViewModel(
                addEditHabitInteractor,
                deleteHabitInteractor,
                getHabitInteractor
            )
            val result = mainViewMode.habitList.first()

            //assert
            assertEquals(resultNetwork, result)
            verify(addEditHabitInteractor).insertHabitCache(list)
            verify(getHabitInteractor).getHabits()
            verify(getHabitInteractor, times(2)).getHabitCache()
        }


    @Test
    fun `correct search test`() = runBlocking<Unit> {
        val habit = getHabitMock()

        val habitResultClass = HabitResult.ValidResult(
            good = listOf(
                habit.copy(id = "2", isCompleted = false, type = 0),
                habit.copy(id = "4", colorId = 123, isCompleted = true, type = 0),
                habit.copy(id = "6", colorId = 123, isCompleted = true, type = 0),
                habit.copy(id = "8", title = "unique", isCompleted = true, type = 0),
            ).shuffled(),
            bad = listOf(
                habit.copy(id = "1", isCompleted = false, type = 1),
                habit.copy(id = "3", isCompleted = false, type = 1),
                habit.copy(id = "5", colorId = 123, isCompleted = true, type = 1),
                habit.copy(id = "7", title = "unique", isCompleted = true, type = 1),
                habit.copy(id = "9", title = "unique", isCompleted = true, type = 1)
            ).shuffled()

        )
        val completedList = listOf(
            habit.copy(id = "1", isCompleted = false, type = 1),
            habit.copy(id = "2", isCompleted = false, type = 0),
            habit.copy(id = "3", isCompleted = false, type = 1),
        )
        val coloredList = listOf(
            habit.copy(id = "4", colorId = 123, isCompleted = true, type = 0),
            habit.copy(id = "5", colorId = 123, isCompleted = true, type = 1),
            habit.copy(id = "6", colorId = 123, isCompleted = true, type = 0),
        )
        val withTitleList = listOf(
            habit.copy(id = "7", title = "unique", isCompleted = true, type = 1),
            habit.copy(id = "8", title = "unique", isCompleted = true, type = 0),
            habit.copy(id = "9", title = "unique", isCompleted = true, type = 1),
        )
        whenever(getHabitInteractor.getHabitCache())
            .thenReturn(flowOf(habitResultClass))

        val mainViewMode = MainViewModel(
            addEditHabitInteractor,
            deleteHabitInteractor,
            getHabitInteractor
        )
        //act
        mainViewMode.search("", true, null)
        val completedListResult = mainViewMode.habitList.replayCache[0] as HabitResult.ValidResult
        mainViewMode.clearFilter()

        mainViewMode.search("", false, 123)
        val coloredListResult = mainViewMode.habitList.replayCache[0] as HabitResult.ValidResult
        mainViewMode.clearFilter()

        mainViewMode.search("unique", false, null)
        val withTitleListResult = mainViewMode.habitList.replayCache[0] as HabitResult.ValidResult
        mainViewMode.clearFilter()

        //assert
        assertEquals(
            completedList,
            (completedListResult.bad!! + completedListResult.good!!).sortedBy { it.id }
        )
        assertEquals(
            coloredList,
            (coloredListResult.bad!! + coloredListResult.good!!).sortedBy { it.id }
        )
        assertEquals(
            withTitleList,
            (withTitleListResult.bad!! + withTitleListResult.good!!).sortedBy { it.id }
        )
    }
}