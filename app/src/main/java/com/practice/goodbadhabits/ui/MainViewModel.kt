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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val addEditHabitInteractor: AddEditHabitInteractor,
    private val deleteHabitInteractor: DeleteHabitInteractor,
    private val getHabitInteractor: GetHabitInteractor
) : ViewModel() {

    private val handler = CoroutineExceptionHandler(::logError)

    //search parameters
    var isOnlyCompleted = false
    var colorSearchFilter: Int? = null

    init {
        initList()
    }

    private val _habitList =
        MutableSharedFlow<HabitResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val habitList get() = _habitList.asSharedFlow()





    private fun initList() = viewModelScope.launch(handler) {
        launch {
            //subscribe on database changes
            getHabitInteractor.getHabitCache()
                .collect {
                    _habitList.emit(it)
                }
        }
        launch {
            //fetch data from network and put to the database
            getHabitInteractor.getHabit()
                .collect {
                    Log.e("TAG", "initList: $it")
                    addEditHabitInteractor.insertHabitCache(it)
                }
        }
    }

    fun clearData() = viewModelScope.launch {
        deleteHabitInteractor.clearData()
    }

    fun addDoneHabit(habitId: String, date: Long) = viewModelScope.launch {
        addEditHabitInteractor.setHabitDone(habitId, date)
        initList()
    }



    fun onSearchTextChanged(habitTitle: String) = viewModelScope.launch {
        getHabitInteractor.searchInCache(
            habitTitle,
            isOnlyCompleted,
            colorSearchFilter
        )
            .collect{ _habitList.emit(it) }
    }
}





