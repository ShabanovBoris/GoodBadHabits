package com.practice.goodbadhabits.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.entities.HabitResult
import com.practice.goodbadhabits.utils.logError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: HabitRepository) : ViewModel() {

    private val handler = CoroutineExceptionHandler(::logError)

    init {
        viewModelScope.launch(handler) {
            initList()
        }
    }

    private val _habitList =
        MutableSharedFlow<HabitResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val habitList get() = _habitList.asSharedFlow()


    private suspend fun initList() {
        //fetch data from network and put to the database
        repository.fetchHabits()
            .collect {
                repository.insertHabitsCache(it)
            }

        //subscribe on database changes
        repository.getHabitsCache()
            .distinctUntilChanged()
            .map { habitList ->
                if (habitList.isEmpty()) {
                    return@map HabitResult.EmptyResult
                } else {
                    return@map HabitResult.ValidResult(
                        habitList.filter { it.type == Habit.Type.GOOD.ordinal },
                        habitList.filter { it.type == Habit.Type.BAD.ordinal }
                    )
                }
            }
            .collect {
                _habitList.emit(it)
            }
    }

      fun clearData() = viewModelScope.launch {
              repository.fetchHabits().collect { list ->
                  list.forEach { habit ->
                      repository.deleteHabit(habit.id)
                  }
              }
              repository.clearCache()
          }



}