package com.practice.goodbadhabits.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.practice.data.repositories.habits.HabitRepositoryImpl
import com.practice.goodbadhabits.utils.launchInWhenStarted
import com.practice.data.utils.logError
import com.practice.domain.repositories.HabitRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryImpl: HabitRepository) : ViewModel() {

    private val handler = CoroutineExceptionHandler(::logError)

    //search parameters
    var isOnlyCompleted = false
    var colorSearchFilter: Int? = null

    init {
        initList()
    }

    private val _habitList =
        MutableSharedFlow<com.practice.domain.entities.HabitResult>(1, 0, BufferOverflow.DROP_OLDEST)
    val habitList get() = _habitList.asSharedFlow()


    private fun initList() = viewModelScope.launch(handler) {
        launch {
            //subscribe on database changes
            repositoryImpl.getHabitsCache()
                .distinctUntilChanged()
                .map { habitList ->
                    if (habitList.isEmpty()) {
                        return@map com.practice.domain.entities.HabitResult.EmptyResult
                    } else {
                        return@map com.practice.domain.entities.HabitResult.ValidResult(
                            habitList.filter { it.type == com.practice.domain.entities.Habit.Type.GOOD.ordinal },
                            habitList.filter { it.type == com.practice.domain.entities.Habit.Type.BAD.ordinal }
                        )

                    }
                }
                .collect {
                    _habitList.emit(it)
                }
        }
        launch {
            //fetch data from network and put to the database
            repositoryImpl.fetchHabits()
                .collect {
                    Log.e("TAG", "initList: $it")
                    repositoryImpl.insertHabitsCache(it)
                }
        }
    }

    fun clearData() = viewModelScope.launch {
        repositoryImpl.fetchHabits().collect { list ->
            list.forEach { habit ->
                repositoryImpl.deleteHabit(habit.id)
            }
        }
        repositoryImpl.clearCache()
    }

    fun addDoneHabit(habitId: String, date: Long) = viewModelScope.launch {
        repositoryImpl.setDoneHabit(habitId, date)
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
        repositoryImpl.getHabitsCache()
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
                    com.practice.domain.entities.HabitResult.ValidResult(
                        result.filter { it.type == com.practice.domain.entities.Habit.Type.GOOD.ordinal },
                        result.filter { it.type == com.practice.domain.entities.Habit.Type.BAD.ordinal }
                    )
                )
            }
}





