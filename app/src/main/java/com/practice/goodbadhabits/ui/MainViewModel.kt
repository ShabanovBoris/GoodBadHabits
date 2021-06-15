package com.practice.goodbadhabits.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: HabitRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
//                repository.uploadHabit(
//                    Habit(
//                        "qwert",
//                        R.color.secondaryColor_600,
//                        3,
//                        false,
//                        1232,
//                        "",
//                        emptyList(),
//                        2,
//                        "describtion",
//                        2,
//                        1
//                    )
//                )


            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    private val _habitList = MutableSharedFlow<List<Habit>>()
    val habitList get() = _habitList.asSharedFlow()


    fun getList() {
        viewModelScope.launch {
            val resp = repository.fetchHabits()
            println(resp)
            _habitList.emit(resp)
//            _habitList.emitAll(repository.getHabitsCache())
        }
    }

    private fun setHabitListCache(list: List<Habit>) {
        viewModelScope.launch {
            repository.insertHabitsCache(list)
        }
    }
}