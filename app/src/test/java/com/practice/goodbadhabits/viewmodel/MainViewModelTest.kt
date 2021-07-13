package com.practice.goodbadhabits.viewmodel

import com.practice.domain.common.HabitResult
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.goodbadhabits.TestCoroutineRule
import com.practice.goodbadhabits.habit.getHabitMockList
import com.practice.goodbadhabits.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
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
            verify(getHabitInteractor).getHabitCache()
        }
}