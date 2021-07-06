package com.practice.goodbadhabits

import com.practice.domain.entities.Habit
import com.practice.domain.entities.HabitManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ExampleUnitTest {
    private val oneDayInMillis = 1000 * 60 * 60 * 24
    private val habitMockGoodDone = Habit(
        title = "",
        colorId = 0,
        repeatDays = 5,
        isCompleted = false,
        createDate = Date().time - oneDayInMillis,
        id = "",
        doneDates = listOf(Date().time),
        count = 2,
        description = "",
        priority = 0,
        type = 0
    )

    @InternalCoroutinesApi
    @Test
    fun `test 1`() {
        runBlocking {
            flowOf(listOf(habitMockGoodDone))
                .onEach { list: List<Habit> ->
                    list.forEach {
                        if (HabitManager(it).isDoneLoop) {
                            coroutineScope {
                                println("delete at $it")
                                println(
                                    "upload at ${
                                        it.copy(
                                            createDate = Date().time,
                                            isCompleted = false
                                        )
                                    }"
                                )
                            }
                        }
                    }
                }
                .launchIn(this)
        }
    }


    @Test
    fun `test 2`() {
        runBlocking {
            flowOf(listOf(habitMockGoodDone.copy(createDate = Date().time - 5 * oneDayInMillis)))
                .onEach { list: List<Habit> ->
                    list.forEach {
                        println("${HabitManager(it).isDoneLoop}")
                        if (HabitManager(it).isDoneLoop) {
                            println("delete at $it")
                            it.createDate = Date().time
                            it.isCompleted = false
                                    println(
                                        "upload at $it"
                                    )

                        }
                    }
                }
                .launchIn(this)
        }
    }
}
