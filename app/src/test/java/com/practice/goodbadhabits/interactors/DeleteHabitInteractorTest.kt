package com.practice.goodbadhabits.interactors

import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.repositories.HabitRepository
import com.practice.goodbadhabits.habit.getHabitMockList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*

class DeleteHabitInteractorTest {

    private val repository: HabitRepository = mock()
    private val deleteHabitInteractor = spy(DeleteHabitInteractor(repository))


    @Test
    fun `clearData() delete all the habits`() = runBlocking{
        val habitList = getHabitMockList(7)
        whenever(repository.fetchHabits()).thenReturn(flowOf(habitList))

        deleteHabitInteractor.clearData()

        verify(repository, times(7)).deleteHabit(any())
        verify(repository).clearCache()
    }
}