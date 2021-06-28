package com.practice.goodbadhabits.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.practice.goodbadhabits.data.HabitRepository
import com.practice.goodbadhabits.entities.Habit
import com.practice.goodbadhabits.entities.HabitResult
import com.practice.goodbadhabits.utils.launchInWhenStarted
import com.practice.goodbadhabits.utils.logError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: HabitRepository) : ViewModel() {

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
        launch {
            //fetch data from network and put to the database
            repository.fetchHabits()
                .collect {
                    Log.e("TAG", "initList: $it")
                    repository.insertHabitsCache(it)
                }
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

    fun addDoneHabit(habitId: String, date: Long) = viewModelScope.launch {
        repository.setDoneHabit(habitId, date)
        initList()
    }


    @FlowPreview
    fun onSearchTextChanged(habitTitle: String, lifecycleOwner: LifecycleOwner) =
        flowOf(habitTitle)
            .debounce(400)
            .distinctUntilChanged()
            .onEach (::searchInCache)
            .launchInWhenStarted(lifecycleOwner.lifecycle.coroutineScope)


    private suspend fun searchInCache(habitTitle: String) =
        repository.getHabitsCache()
            .map { list ->
                list.filter { habit ->
                    habit.title.contains(habitTitle, true)
                }
            }
            .collect {
                var result = it
                if (isOnlyCompleted){
                    result = it.filter { habit -> habit.isCompleted == false  }
                }
                if (colorSearchFilter != null){
                    result = result.filter { habit -> habit.colorId == colorSearchFilter}
                }

                _habitList.emit(
                    HabitResult.ValidResult(
                        result.filter { it.type == Habit.Type.GOOD.ordinal },
                        result.filter { it.type == Habit.Type.BAD.ordinal }
                    )
                )
            }
}





