package com.practice.goodbadhabits.interactors

import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.repositories.HabitRepository
import com.practice.goodbadhabits.habit.getHabitMock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AddHabitInteractorTest {

    private val habit = getHabitMock()
    private val repository: HabitRepository = mock()
    private val addHabitInteractor = AddEditHabitInteractor(repository)

    @Test
    fun `uploadHabit() returns right value`(): Unit = runBlocking{
        //arrange
        whenever(repository.uploadHabit(habit)).thenReturn(
            "success"
        )
        //act
        val test = addHabitInteractor.uploadHabit(habit)
        //assert
        assertEquals("success",test)
    }

}