//package com.practice.goodbadhabits.viewmodel
//
//
//import com.practice.domain.interactors.GetHabitInteractor
//import com.practice.goodbadhabits.TestCoroutineRule
//import com.practice.goodbadhabits.habit.getHabitMock
//import com.practice.goodbadhabits.ui.dashboard.DashboardViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Assert.assertEquals
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//import kotlin.time.ExperimentalTime
//
//@ExperimentalTime
//@ExperimentalCoroutinesApi
//class DashboardViewModelTest {
//
//    @get:Rule
//    val coroutineRule = TestCoroutineRule()
//
//    private val getHabitInteractor: GetHabitInteractor = mock()
//
//    @Test
//    fun `flow is emit the correct habit type`() = runBlockingTest {
//        val habit = getHabitMock()
//        val testList = listOf(
//            habit.copy(type = 0),
//            habit.copy(type = 0),
//            habit.copy(type = 1),
//            habit.copy(type = 0),
//        )
//        val testListWithNewHabit = listOf(
//            habit.copy(title = "new habit", type = 1),
//            habit.copy(type = 0),
//            habit.copy(type = 0),
//            habit.copy(type = 1),
//            habit.copy(type = 0),
//        )
//        val job: Job
//        whenever(getHabitInteractor.getRawHabitCache()).thenReturn(
//            flow {
//                emit(testList) // init value must be ignored
//                emit(testList) // the same value must be ignored
//                emit(
//                    testListWithNewHabit
//                )
//            }
//        )
//        //act
//        val vm = DashboardViewModel(getHabitInteractor)
//        var resType: Int = -1
//        job = launch {
//            vm.sharedFlow.collect { resType = it }
//        }
//        //assert
//        assertEquals(1, resType)
//
//        job.cancel()
//    }
//}