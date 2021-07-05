package com.practice.goodbadhabits.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.data.utils.logError
import com.practice.domain.common.HabitResult
import com.practice.domain.interactors.AddEditHabitInteractor
import com.practice.domain.interactors.DeleteHabitInteractor
import com.practice.domain.interactors.GetHabitInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val addEditHabitInteractor: AddEditHabitInteractor,
    private val deleteHabitInteractor: DeleteHabitInteractor,
    private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler(::logError)

    private val _habitList =
        MutableSharedFlow<HabitResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val habitList get() = _habitList.asSharedFlow()

    //search parameters
    var onlyNotCompleted = false
    var colorSearchFilter: Int? = null

    init {
        initList()
    }

    private fun initList() = viewModelScope.launch(exceptionHandler) {
        launch {
            getHabitInteractor.getHabitCache()
                .collect {
                    Log.e("TAG", "get data from db: $it")
                    _habitList.emit(it)
                }
        }
        launch {
            getHabitInteractor.getHabits()
                .collect {
                    Log.e("TAG", "update fresh data from network: $it")
                    addEditHabitInteractor.insertHabitCache(it)
                }
        }
    }

    fun clearData() = viewModelScope.launch(exceptionHandler) {
        deleteHabitInteractor.clearData()
    }

    fun addDoneHabit(habitId: String, date: Long) = viewModelScope.launch(exceptionHandler) {
        addEditHabitInteractor.setHabitDone(habitId, date)
        initList()
    }

    fun onSearchTextChanged(habitTitle: String) = viewModelScope.launch(exceptionHandler) {
        getHabitInteractor.searchInCache(
            habitTitle,
            onlyNotCompleted,
            colorSearchFilter
        )
            .collect{ _habitList.emit(it) }
    }
}





