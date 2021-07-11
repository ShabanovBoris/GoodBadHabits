package com.practice.goodbadhabits.viewmodel

import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import com.practice.goodbadhabits.habit.getHabitMock
import com.practice.goodbadhabits.habit.getHabitMockList
import com.practice.goodbadhabits.ui.addition.AdditionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AdditionViewModelTest {

//    @get:Rule
//    val coroutineRule = TestCoroutineRule()


    private val addEditHabitInteractor: AddEditHabitInteractor = mock()
    private val deleteHabitInteractor: DeleteHabitInteractor = mock()
    private val getHabitInteractor: GetHabitInteractor = mock()


    @Test
    fun `add habit should invoke network and cache`() = runBlockingTest {
        //arrange
        val list = getHabitMockList(5)
        val habitTest = getHabitMock()
        whenever(getHabitInteractor.getHabits()).thenReturn(flowOf(list))
        val additionViewModel = AdditionViewModel(
            addEditHabitInteractor,
            deleteHabitInteractor,
            getHabitInteractor
        )
        //act
        additionViewModel.addHabit(habitTest)
        //assert
        verify(addEditHabitInteractor).uploadHabit(habitTest)
        verify(addEditHabitInteractor).insertHabitCache(list)
    }

    @Test
    fun `state is changes correct`() = runBlockingTest {
        //arrange
        val list = getHabitMockList(5)
        val habitTest = getHabitMock()
        whenever(getHabitInteractor.getHabits()).thenReturn(flowOf(list))
        val additionViewModel = AdditionViewModel(
            addEditHabitInteractor,
            deleteHabitInteractor,
            getHabitInteractor
        )
        //act
        var isLoading = false
        var isEmpty = false
        var isComplete = false
        val job = launch {
            additionViewModel.actionStateFlow.collect {
                when (it) {
                    AdditionViewModel.ActionState.LOADING -> isLoading = true
                    AdditionViewModel.ActionState.COMPLETE -> isComplete = true
                    AdditionViewModel.ActionState.EMPTY -> isEmpty = true
                }
            }
        }
        additionViewModel.addHabit(habitTest)
        //assert
        assert(isComplete)
        assert(isLoading)
        assert(isEmpty)
        job.cancel()
    }
}